package com.neo.mypatients.patient.domain.repository

import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.LocalPatient
import com.neo.mypatients.patient.domain.model.Patient
import kotlinx.coroutines.flow.Flow

interface PatientRepository {

    suspend fun upsertPatient(patient: Patient): Resource<Unit, DataError.Local>

    suspend fun softDeletePatient(id: Long): Resource<Unit, DataError.Local>

    suspend fun deletePatient(id: Long): Resource<Unit, DataError.Local>

    suspend fun getPatient(id: Long): Resource<Patient, DataError.Local>

    fun getPatientsByName(
        name: String = "",
    ): Flow<Resource<List<Patient>, DataError.Local>>

    suspend fun syncPatients(): Resource<Unit, DataError.Remote>

}