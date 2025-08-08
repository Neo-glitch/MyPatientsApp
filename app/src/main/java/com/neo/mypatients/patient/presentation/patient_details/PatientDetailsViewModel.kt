package com.neo.mypatients.patient.presentation.patient_details

import androidx.lifecycle.ViewModel
import com.neo.mypatients.patient.domain.usecases.GetPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientDetailsViewModel @Inject constructor(
    private val getPatientUseCase: GetPatientUseCase
): ViewModel() {

}