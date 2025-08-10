package com.neo.mypatients.patient

import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.patient.domain.repository.PatientRepository
import com.neo.mypatients.utils.dummyPatients
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePatientRepository: PatientRepository {

    private val invalidAge = 500

    private val patients = MutableList(dummyPatients.size) { index ->
        dummyPatients[index]
    }

    override suspend fun upsertPatient(patient: Patient): Resource<Unit, DataError.Local> {
        if (patient.age == invalidAge) {
            return Resource.Error(DataError.Local.DATABASE_ERROR)
        }

        val isPatientPresent = patients.any { it.id == patient.id }
        if (isPatientPresent) {
            val index = patients.indexOfFirst { it.id == patient.id }
            patients[index] = patient
        } else {
            patients.add(patient)
        }
        return Resource.Success(Unit)
    }

    override suspend fun softDeletePatient(id: Long): Resource<Unit, DataError.Local> {
        val index = patients.indexOfFirst { it.id == id }
        patients[index] = patients[index].copy(syncStatus = SyncStatus.PENDING_DELETE)
        return Resource.Success(Unit)
    }

    override suspend fun deletePatient(id: Long): Resource<Unit, DataError.Local> {
        val index = patients.indexOfFirst { it.id == id }
        patients.removeAt(index)
        return Resource.Success(Unit)
    }

    override suspend fun getPatient(id: Long): Resource<Patient, DataError.Local> {
        val patient = patients.find { it.id == id }
        return if (patient != null) {
            Resource.Success(patient)
        } else {
            Resource.Error(DataError.Local.DATABASE_ERROR)
        }
    }

    override fun getPatientsByName(name: String): Flow<Resource<List<Patient>, DataError.Local>> {
        val filteredPatients = patients.filter { it.name.contains(name, ignoreCase = true) }
        return flowOf(Resource.Success(filteredPatients))
    }

    override suspend fun syncPatients(): Resource<Unit, DataError.Remote> {
        patients.forEach { it.copy(syncStatus = SyncStatus.SYNCED) }
        return Resource.Success(Unit)
    }


}