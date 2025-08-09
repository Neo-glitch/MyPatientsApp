package com.neo.mypatients.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object PatientsScreen

@Serializable
data class PatientDetailsScreen(
    val patientId: Long,
)

@Serializable
data class AddEditPatientDetailsScreen(
    val patientId: Long? = null,
)
