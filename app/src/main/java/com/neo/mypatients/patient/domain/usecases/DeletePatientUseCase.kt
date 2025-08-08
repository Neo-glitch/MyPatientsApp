package com.neo.mypatients.patient.domain.usecases

import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.patient.domain.repository.PatientRepository

class DeletePatientUseCase(
    private val repository: PatientRepository
) {

    suspend operator fun invoke(patient: Patient): Resource<Unit, DataError.Local> {
        return if (patient.syncStatus == SyncStatus.PENDING_CREATE) {
            repository.deletePatient(patient.id)
        } else {
            repository.softDeletePatient(patient.id)
        }
    }

}