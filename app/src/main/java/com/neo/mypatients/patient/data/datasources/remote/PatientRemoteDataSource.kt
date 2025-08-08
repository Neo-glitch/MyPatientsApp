package com.neo.mypatients.patient.data.datasources.remote

interface PatientRemoteDataSource {
    suspend fun syncPatient()
}