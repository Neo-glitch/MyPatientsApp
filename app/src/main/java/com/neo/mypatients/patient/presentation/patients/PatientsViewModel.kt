package com.neo.mypatients.patient.presentation.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.core.utils.toUiText
import com.neo.mypatients.patient.domain.usecases.GetPatientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PatientsViewModel @Inject constructor(
    private val getPatientsUseCase: GetPatientsUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(PatientsUiState())
    val uiState: StateFlow<PatientsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.map { it.query }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    getPatientsUseCase(name = query, age = null, gender = null)
                }
                .collectLatest { patientsResponse ->
                    val patients = _uiState.value.patients
                    when (patientsResponse) {
                        is Resource.Error -> {
                            if (patients.isEmpty()) {
                                _uiState.value = _uiState.value.copy(
                                    loadState = PatientsLoadState.Error(patientsResponse.error.toUiText())
                                )
                            }
                        }
                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    loadState = PatientsLoadState.Success,
                                    patients = patientsResponse.data
                                )
                            }
                        }
                    }
                }
        }
    }

    fun onQueryUpdate(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
    }
}