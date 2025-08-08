package com.neo.mypatients.patient.domain.model

import androidx.room.ColumnInfo
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus

data class Patient(
    val id: Long = 0,
    val name: String,
    val age: Int,
    val gender: Gender,
    val phoneNumber: String,
    val medicalCondition: String,
    val syncStatus: SyncStatus
)