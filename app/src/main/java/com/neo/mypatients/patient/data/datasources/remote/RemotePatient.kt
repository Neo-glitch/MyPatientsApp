package com.neo.mypatients.patient.data.datasources.remote

import androidx.room.ColumnInfo
import com.neo.mypatients.patient.data.datasources.local.model.Gender

data class RemotePatient(
    val name: String,
    val age: Int,
    val gender: Gender,
    val phoneNumber: String,
    val medicalCondition: String,
)