package com.neo.mypatients.patient.data.datasources.local

import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.LocalPatient
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import kotlinx.coroutines.flow.Flow

interface PatientLocalDataSource {

    suspend fun upsertPatient(patient: LocalPatient)

    suspend fun softDeletePatient(id: Long)

    suspend fun deletePatient(id: Long)

    suspend fun getPatientBySyncStatus(status: SyncStatus): List<LocalPatient>

    suspend fun getPatient(id: Long): LocalPatient

    fun getPatientsByOptionalFilters(
        name: String,
        age: Int? = null,
        gender: Gender? = null
    ): Flow<List<LocalPatient>>

}