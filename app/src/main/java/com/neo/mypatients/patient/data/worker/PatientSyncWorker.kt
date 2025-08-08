package com.neo.mypatients.patient.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.patient.domain.repository.PatientRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class PatientSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val patientRepository: PatientRepository
): CoroutineWorker(appContext, workerParams){

    override suspend fun doWork(): Result {
        return try {
            val response = patientRepository.syncPatients()
            when (response) {
                is Resource.Error<*> -> Result.retry()
                is Resource.Success<*> -> Result.success()
            }
            Result.success()
        }catch (e: Exception){
            Result.failure()
        }
    }
}