package com.neo.mypatients.patient.presentation.patients

import androidx.lifecycle.ViewModel
import com.neo.mypatients.patient.domain.usecases.GetPatientsUseCase
import javax.inject.Inject


class PatientsViewModel @Inject constructor(
    private val getPatientsUseCase: GetPatientsUseCase,
): ViewModel() {
}