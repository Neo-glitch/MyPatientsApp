package com.neo.mypatients.patient.data.repository

import com.google.common.truth.Truth.assertThat
import com.neo.mypatients.MainDispatcherRule
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.patient.data.datasources.local.PatientLocalDataSource
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.data.datasources.remote.PatientRemoteDataSource
import com.neo.mypatients.patient.data.mapper.toLocal
import com.neo.mypatients.patient.domain.repository.PatientRepository
import com.neo.mypatients.utils.dummyPatient
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PatientRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var remoteDataSource: PatientRemoteDataSource

    @Mock
    lateinit var localDataSource: PatientLocalDataSource
    lateinit var repository: PatientRepository


    @Before
    fun setUp() {
        repository = PatientRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `upsertPatient should return Success when localDatasource call succeeds`() = runTest {
        val patient = dummyPatient
        whenever(localDataSource.upsertPatient(any())).thenReturn(Unit)
        val result = repository.upsertPatient(patient)

        assertTrue(result is Resource.Success)
        verify(localDataSource).upsertPatient(patient.toLocal())
    }

    @Test
    fun `upsertPatient should return Error when localDatasource throws exception`() = runTest {
        val patient = dummyPatient
        whenever(localDataSource.upsertPatient(any())).thenThrow(IllegalArgumentException())
        val result = repository.upsertPatient(patient)

        assertTrue(result is Resource.Error)
    }

    @Test
    fun `softDeletePatient should return Success when localDatasource call succeeds`() = runTest {
        whenever(localDataSource.softDeletePatient(any())).thenReturn(Unit)

        val result = repository.softDeletePatient(1L)

        assertTrue(result is Resource.Success)
        verify(localDataSource).softDeletePatient(id = 1L)
    }

    @Test
    fun `deletePatient should return Success when localDatasource call succeeds`() = runTest {
        whenever(localDataSource.deletePatient(any())).thenReturn(Unit)

        val result = repository.deletePatient(1L)

        assertTrue(result is Resource.Success)
        verify(localDataSource).deletePatient(1L)
    }

    @Test
    fun `getPatient should return Success with mapped domain object`() = runTest {
        val localPatient = dummyPatient.toLocal()
        whenever(localDataSource.getPatient(any())).thenReturn(localPatient)

        val result = repository.getPatient(1L)

        assertTrue(result is Resource.Success)
        assertThat(localPatient.name).isEqualTo((result as Resource.Success).data.name)
    }

    @Test
    fun `getPatientsByName should emit Success with mapped list`() = runTest {
        val localPatients = listOf(dummyPatient.toLocal())
        whenever(localDataSource.getPatientsByName("John")).thenReturn(flowOf(localPatients))

        val result = repository.getPatientsByName("John").first()

        assertTrue(result is Resource.Success)
        assertThat(1).isEqualTo((result as Resource.Success).data.size)
    }

    @Test
    fun `syncPatients should return Success when all sync operations succeed`() = runTest {
        whenever(localDataSource.getPatientBySyncStatus(SyncStatus.PENDING_DELETE)).thenReturn(emptyList())
        whenever(localDataSource.getPatientBySyncStatus(SyncStatus.PENDING_UPDATE)).thenReturn(emptyList())
        whenever(localDataSource.getPatientBySyncStatus(SyncStatus.PENDING_CREATE)).thenReturn(emptyList())

        val result = repository.syncPatients()
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `syncPatients should return Error when any sync operation fails`() = runTest {
        whenever(localDataSource.getPatientBySyncStatus(SyncStatus.PENDING_DELETE)).thenReturn(emptyList())
        whenever(localDataSource.getPatientBySyncStatus(SyncStatus.PENDING_UPDATE)).thenReturn(emptyList())
        whenever(localDataSource.getPatientBySyncStatus(SyncStatus.PENDING_CREATE)).thenReturn(listOf(dummyPatient.toLocal()))
        whenever(remoteDataSource.syncPatient()).thenThrow(IllegalArgumentException())

        val result = repository.syncPatients()
        assertTrue(result is Resource.Error)
    }



}