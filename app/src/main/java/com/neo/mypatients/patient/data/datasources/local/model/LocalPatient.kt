package com.neo.mypatients.patient.data.datasources.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class LocalPatient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val age: Int,
    val gender: Gender,
    @ColumnInfo("phone_number")
    val phoneNumber: String,
    val medicalCondition: String,
    @ColumnInfo(name = "sync_status")
    val syncStatus: SyncStatus
)