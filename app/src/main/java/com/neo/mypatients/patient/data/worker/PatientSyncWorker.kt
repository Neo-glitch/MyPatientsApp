package com.neo.mypatients.patient.data.worker

import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.core.utils.NotificationHelper
import com.neo.mypatients.patient.domain.repository.PatientRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class PatientSyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val patientRepository: PatientRepository
): CoroutineWorker(appContext, workerParams){

    override suspend fun doWork(): Result {

        return try {
            val syncNotificationHelper = NotificationHelper(context = appContext)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                setForeground(
                    ForegroundInfo(
                        NotificationHelper.SYNC_FOREGROUND_INFO_NOTIFICATION_ID,
                        syncNotificationHelper.createSyncNotification(),
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
                    )
                )
            } else {
                setForeground(
                    ForegroundInfo(
                        NotificationHelper.SYNC_FOREGROUND_INFO_NOTIFICATION_ID,
                        syncNotificationHelper.createSyncNotification(),
                    )
                )
            }

            // to allow seeing the notification for syncing
            delay(4000)
            val response = patientRepository.syncPatients()
            when (response) {
                is Resource.Error<*> -> Result.retry()
                is Resource.Success<*> -> Result.success()
            }
        }catch (e: Exception){
            Result.failure()
        }
    }
}