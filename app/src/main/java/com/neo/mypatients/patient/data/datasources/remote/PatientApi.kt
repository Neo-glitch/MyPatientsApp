package com.neo.mypatients.patient.data.datasources.remote

import retrofit2.http.GET

interface PatientApi {

    @GET
    suspend fun syncPatientData(): Unit
}