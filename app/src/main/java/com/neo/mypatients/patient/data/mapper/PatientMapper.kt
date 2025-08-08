package com.neo.mypatients.patient.data.mapper

import com.neo.mypatients.patient.data.datasources.local.model.LocalPatient
import com.neo.mypatients.patient.domain.model.Patient

fun LocalPatient.toDomain(): Patient {
    return Patient(
        id = id,
        name = name,
        age = age,
        gender = gender,
        syncStatus = syncStatus,
        phoneNumber = phoneNumber,
        medicalCondition = medicalCondition
    )
}

fun Patient.toLocal(): LocalPatient {
    return LocalPatient(
        id = id,
        name = name,
        age = age,
        gender = gender,
        syncStatus = syncStatus,
        phoneNumber = phoneNumber,
        medicalCondition = medicalCondition
    )
}

