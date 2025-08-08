package com.neo.mypatients.patient.data.repository

import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.core.network.NetworkHelper
import com.neo.mypatients.core.utils.GeneralExceptionHandler
import com.neo.mypatients.patient.data.datasources.local.PatientLocalDataSource
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.LocalPatient
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.data.datasources.remote.PatientRemoteDataSource
import com.neo.mypatients.patient.data.mapper.toDomain
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.patient.domain.repository.PatientRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

class PatientRepositoryImpl(
    private val localDataSource: PatientLocalDataSource,
    private val remoteDataSource: PatientRemoteDataSource
): PatientRepository {

    override suspend fun upsertPatient(patient: LocalPatient): Resource<Unit, DataError.Local> {
        return try {
            localDataSource.upsertPatient(patient)
            Resource.Success(Unit)
        } catch (throwable: Throwable) {
            Resource.Error(GeneralExceptionHandler.getLocalError(throwable))
        }
    }

    override suspend fun softDeletePatient(id: Long): Resource<Unit, DataError.Local> {
        try {
            localDataSource.softDeletePatient(id)
            return Resource.Success(Unit)
        } catch (throwable: Throwable) {
            return Resource.Error(GeneralExceptionHandler.getLocalError(throwable))
        }
    }

    override suspend fun deletePatient(id: Long): Resource<Unit, DataError.Local> {
        return try {
            localDataSource.deletePatient(id)
            Resource.Success(Unit)
        } catch (throwable: Throwable) {
            Resource.Error(GeneralExceptionHandler.getLocalError(throwable))
        }
    }

    override fun getPatientsByOptionalFilters(
        name: String?,
        age: Int?,
        gender: Gender?
    ): Flow<Resource<List<Patient>, DataError.Local>> {
        return localDataSource.getPatientsByOptionalFilters(name, age, gender)
            .map { patients ->
                Resource.Success(patients.map { it.toDomain() })
            }.catch { throwable ->
                val error = GeneralExceptionHandler.getLocalError(throwable)
                Resource.Error(error)
            }

    }

    override suspend fun syncPatients(): Resource<Unit, DataError.Remote> {
        return try {
            var result: Resource<Unit, DataError.Remote> = Resource.Success(Unit)

            val pendingDeletePatients = localDataSource.getPatientBySyncStatus(SyncStatus.PENDING_DELETE)
            val pendingUpdatePatients = localDataSource.getPatientBySyncStatus(SyncStatus.PENDING_UPDATE)
            val pendingInsertPatients = localDataSource.getPatientBySyncStatus(SyncStatus.PENDING_CREATE)

            supervisorScope {
                val response = listOf(
                    async { syncPendingDeletePatients(pendingDeletePatients) },
                    async { syncPendingUpdatePatients(pendingUpdatePatients) },
                    async { syncPendingInsertPatients(pendingInsertPatients) }
                ).awaitAll()

                if (response.any { it is Resource.Error }) {
                    result = response.first { it is Resource.Error } as Resource.Error
                }
            }

            return result
        } catch (throwable: Throwable) {
            Resource.Error(GeneralExceptionHandler.getNetworkError(throwable))
        }
    }

    private suspend fun syncPendingDeletePatients(
        pendingDeletePatients: List<LocalPatient>
    ) : Resource<Unit, DataError.Remote> {
        var result: Resource<Unit, DataError.Remote> = Resource.Success(Unit)

        pendingDeletePatients.forEach { patient ->
            val response = NetworkHelper.handleApiCall { remoteDataSource.syncPatient() }

            when (response) {
                is Resource.Error -> {
                    result = Resource.Error(response.error)
                }
                is Resource.Success<*> -> {
                    localDataSource.deletePatient(patient.id)
                }
            }
        }

        return result
    }

    private suspend fun syncPendingUpdatePatients(
        pendingUpdatePatients: List<LocalPatient>
    ) : Resource<Unit, DataError.Remote> {
        var result: Resource<Unit, DataError.Remote> = Resource.Success(Unit)

        pendingUpdatePatients.forEach { patient ->
            val response = NetworkHelper.handleApiCall { remoteDataSource.syncPatient() }

            when (response) {
                is Resource.Error -> {
                    result = Resource.Error(response.error)
                }
                is Resource.Success<*> -> {
                    val updatedPatient = patient.copy(syncStatus = SyncStatus.SYNCED)
                    localDataSource.upsertPatient(updatedPatient)
                }
            }
        }
        return result
    }

    private suspend fun syncPendingInsertPatients(
        pendingInsertPatients: List<LocalPatient>
    ) : Resource<Unit, DataError.Remote> {
        var result: Resource<Unit, DataError.Remote> = Resource.Success(Unit)

        pendingInsertPatients.forEach { patient ->
            val response = NetworkHelper.handleApiCall { remoteDataSource.syncPatient() }

            when (response) {
                is Resource.Error -> {
                    result = Resource.Error(response.error)
                }
                is Resource.Success<*> -> {
                    val updatedPatient = patient.copy(syncStatus = SyncStatus.SYNCED)
                    localDataSource.upsertPatient(updatedPatient)
                }
            }
        }
        return result
    }

}