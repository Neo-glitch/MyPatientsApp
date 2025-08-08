package com.neo.mypatients.patient.data.datasources.local

import com.neo.mypatients.core.database.MyPatientAppDatabase
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.LocalPatient
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import kotlinx.coroutines.flow.Flow

class PatientLocalDataSourceImpl(
    private val database: MyPatientAppDatabase
): PatientLocalDataSource {

    private val patientDao = database.patientDao

    override suspend fun upsertPatient(patient: LocalPatient) {
        patientDao.upsertPatient(patient)
    }

    override suspend fun softDeletePatient(id: Long) {
        patientDao.updateSyncStatusById(id, SyncStatus.PENDING_DELETE)
    }

    override suspend fun deletePatient(id: Long) {
        patientDao.deletePatient(id)
    }

    override suspend fun getPatientBySyncStatus(status: SyncStatus): List<LocalPatient> {
        return patientDao.getUsersBySyncStatus(status)
    }

    override fun getPatientsByOptionalFilters(
        name: String?,
        age: Int?,
        gender: Gender?
    ): Flow<List<LocalPatient>> {
        return patientDao.getUsersByOptionalFilters(name, age, gender)
    }
}