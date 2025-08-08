package com.neo.mypatients.patient.domain.usecases

import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.patient.domain.repository.PatientRepository

class AddEditPatientUseCase(
    private val repository: PatientRepository
) {

    suspend operator fun invoke(patient: Patient): Resource<Unit, DataError.Local> {
        return repository.upsertPatient(patient)
    }
}