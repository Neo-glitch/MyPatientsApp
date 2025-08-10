package com.neo.mypatients.patient.data.datasources.remote

class PatientRemoteDataSourceImpl(
    private val patientService: PatientApi
): PatientRemoteDataSource {

    override suspend fun syncPatient() {
        patientService.syncPatientData()
    }
}