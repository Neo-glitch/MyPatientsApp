package com.neo.mypatients.patient.domain.usecases

import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.patient.domain.repository.PatientRepository

class GetPatientUseCase(
    private val repository: PatientRepository
) {

    suspend operator fun invoke(id: Long): Resource<Patient, DataError.Local> {
        return repository.getPatient(id)
    }
}