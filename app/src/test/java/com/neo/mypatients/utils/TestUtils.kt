package com.neo.mypatients.utils

import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.domain.model.Patient

val dummyPatients: List<Patient> =
    listOf(
        Patient(
            id = 1L,
            name = "Onyekachukwu Nweke",
            age = 28,
            gender = Gender.Male,
            phoneNumber = "08012345678",
            medicalCondition = "Hypertension",
            syncStatus = SyncStatus.SYNCED
        ),
        Patient(
            id = 2L,
            name = "Leo Smith",
            age = 34,
            gender = Gender.Female,
            phoneNumber = "08087654321",
            medicalCondition = "Diabetes",
            syncStatus = SyncStatus.PENDING_UPDATE
        ),
        Patient(
            id = 3L,
            name = "Chidi Benson",
            age = 40,
            gender = Gender.Male,
            phoneNumber = "08099887766",
            medicalCondition = "Asthma",
            syncStatus = SyncStatus.PENDING_DELETE
        ),
        Patient(
            id = 4L,
            name = "Sarah Brown",
            age = 22,
            gender = Gender.Female,
            phoneNumber = "08055443322",
            medicalCondition = "Healthy",
            syncStatus = SyncStatus.PENDING_CREATE
        ),
        Patient(
            id = 5L,
            name = "David Kim yun mi",
            age = 55,
            gender = Gender.Male,
            phoneNumber = "08011112222",
            medicalCondition = "Heart Disease",
            syncStatus = SyncStatus.SYNCED
        )
    )

val dummyPatient = Patient(
    id = 1L,
    name = "Onyekachukwu Nweke",
    age = 30,
    gender = Gender.Male,
    phoneNumber = "08045678943",
    medicalCondition = "Hypertension",
    syncStatus = SyncStatus.SYNCED
)