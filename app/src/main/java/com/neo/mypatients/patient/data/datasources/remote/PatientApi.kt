package com.neo.mypatients.patient.data.datasources.remote

import com.neo.mypatients.core.utils.K
import retrofit2.http.GET

interface PatientApi {

    @GET(K.SYNC_ENDPOINT)
    suspend fun syncPatientData(): SyncPatientResponse
}