package com.neo.mypatients.patient.presentation.patients

import androidx.lifecycle.ViewModel
import com.neo.mypatients.patient.domain.usecases.GetPatientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PatientsViewModel @Inject constructor(
    private val getPatientsUseCase: GetPatientsUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(PatientsUiState())
    val uiState: StateFlow<PatientsUiState> = _uiState.asStateFlow()

    fun onQueryUpdate(query: String) {

    }
}