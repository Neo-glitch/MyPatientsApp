package com.neo.mypatients.patient.presentation.add_edit_patient

import androidx.lifecycle.ViewModel
import com.neo.mypatients.patient.domain.usecases.AddEditPatientUseCase
import com.neo.mypatients.patient.domain.usecases.GetPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddEditPatientViewModel @Inject constructor(
    private val addEditPatientUseCase: AddEditPatientUseCase,
): ViewModel() {
}