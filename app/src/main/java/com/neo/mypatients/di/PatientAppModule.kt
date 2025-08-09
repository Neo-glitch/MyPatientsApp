package com.neo.mypatients.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.neo.mypatients.BuildConfig
import com.neo.mypatients.core.data.database.MyPatientAppDatabase
import com.neo.mypatients.core.data.network.NetworkConnectionInterceptor
import com.neo.mypatients.core.utils.K
import com.neo.mypatients.core.utils.K.API_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PatientAppModule {

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Singleton
    @Provides
    fun provideNewsyDatabase(
        @ApplicationContext context: Context,
    ): MyPatientAppDatabase {
        return Room.databaseBuilder(
            context,
            MyPatientAppDatabase::class.java,
            "newsy_db"
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

}