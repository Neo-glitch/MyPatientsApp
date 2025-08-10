package com.neo.mypatients

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.neo.mypatients.patient.data.worker.PatientSyncWorker
import com.neo.mypatients.patient.domain.repository.PatientRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyPatientApplication: Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: CustomWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
    }
}

class CustomWorkerFactory @Inject constructor(private val repository: PatientRepository) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return PatientSyncWorker(appContext, workerParameters, repository)
    }
}
