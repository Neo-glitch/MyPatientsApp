package com.neo.mypatients.patient.data.datasources.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.LocalPatient
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface PatientDao {

    @Upsert
    suspend fun upsertPatient(patient: LocalPatient)

    @Query("UPDATE patients SET sync_status = :status WHERE id = :id")
    suspend fun updateSyncStatusById(id: Long, status: SyncStatus)

    @Query("DELETE FROM patients WHERE id = :id")
    suspend fun deletePatient(id: Long)

    @Query("SELECT * FROM patients WHERE id = :id")
    suspend fun getPatientById(id: Long): LocalPatient

    @Query("""
        SELECT * FROM patients
        WHERE name LIKE '%' || :name || '%'
        ORDER BY id ASC
    """)
    fun getPatientsByName(name: String): Flow<List<LocalPatient>>

    @Query("SELECT * FROM patients WHERE sync_status = :status")
    suspend fun getUsersBySyncStatus(status: SyncStatus): List<LocalPatient>
}