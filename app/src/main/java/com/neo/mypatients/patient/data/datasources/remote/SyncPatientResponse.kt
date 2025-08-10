package com.neo.mypatients.patient.data.datasources.remote

import kotlinx.serialization.Serializable

@Serializable
data class SyncPatientResponse(
	val code: Int,
	val message: String,
	val status: String
)

