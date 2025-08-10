package com.neo.mypatients.patient.domain.usecases

import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.patient.domain.repository.PatientRepository
import kotlin.text.toIntOrNull

class AddEditPatientUseCase(
    private val repository: PatientRepository
) {

    suspend operator fun invoke(
        id: Long?,
        name: String,
        age: Int?,
        gender: Gender,
        phoneNumber: String,
        medicalCondition: String,
        syncStatus: SyncStatus?
    ): Resource<Unit, DataError.Local> {
        val isAddOperation = id == null || syncStatus == null || syncStatus == SyncStatus.PENDING_CREATE
        return repository.upsertPatient(
            Patient(
                id = id ?: 0,
                name = name,
                age = age ?: 1,
                gender = gender,
                phoneNumber = phoneNumber,
                medicalCondition = medicalCondition,
                syncStatus = if (isAddOperation) SyncStatus.PENDING_CREATE else SyncStatus.PENDING_UPDATE,
            )
        )
    }
}