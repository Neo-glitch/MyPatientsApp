package com.neo.mypatients.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neo.mypatients.patient.data.datasources.local.PatientDao
import com.neo.mypatients.patient.data.datasources.local.model.LocalPatient

@Database(entities = [LocalPatient::class], version = 1, exportSchema = true)
@TypeConverters(Converter::class)
abstract class MyPatientAppDatabase: RoomDatabase() {

    abstract val patientDao: PatientDao
}