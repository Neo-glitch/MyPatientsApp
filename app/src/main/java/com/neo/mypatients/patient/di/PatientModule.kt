package com.neo.mypatients.patient.di

import com.neo.mypatients.core.database.MyPatientAppDatabase
import com.neo.mypatients.patient.data.datasources.local.PatientLocalDataSource
import com.neo.mypatients.patient.data.datasources.local.PatientLocalDataSourceImpl
import com.neo.mypatients.patient.data.datasources.remote.PatientApi
import com.neo.mypatients.patient.data.datasources.remote.PatientRemoteDataSource
import com.neo.mypatients.patient.data.repository.PatientRepositoryImpl
import com.neo.mypatients.patient.domain.repository.PatientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PatientModule {

    @Singleton
    @Provides
    fun providePatientApi(retrofit: Retrofit) : PatientApi {
        return retrofit.create(PatientApi::class.java)
    }

    @Singleton
    @Provides
    fun providePatientLocalDataSource(database: MyPatientAppDatabase) : PatientLocalDataSource {
        return PatientLocalDataSourceImpl(database)
    }

    @Singleton
    @Provides
    fun providePatientRepository(
        localDataSource: PatientLocalDataSource,
        remoteDataSource: PatientRemoteDataSource
    ) : PatientRepository {
        return PatientRepositoryImpl(localDataSource, remoteDataSource)
    }

}