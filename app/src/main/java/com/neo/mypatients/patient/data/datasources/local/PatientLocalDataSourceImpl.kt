package com.neo.mypatients.patient.data.datasources.local

import com.neo.mypatients.patient.data.datasources.local.model.LocalPatient
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import kotlinx.coroutines.flow.Flow

class PatientLocalDataSourceImpl(
    private val patientDao: PatientDao
): PatientLocalDataSource {

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
        return patientDao.getPatientsBySyncStatus(status)
    }

    override suspend fun getPatient(id: Long): LocalPatient {
        return patientDao.getPatientById(id)
    }

    override fun getPatientsByName(
        name: String,
    ): Flow<List<LocalPatient>> {
        return patientDao.getPatientsByName(name)
    }
}