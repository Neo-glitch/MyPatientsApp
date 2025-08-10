package com.neo.mypatients.patient.presentation.patient_details

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.neo.mypatients.MainDispatcherRule
import com.neo.mypatients.patient.FakePatientRepository
import com.neo.mypatients.patient.domain.usecases.DeletePatientUseCase
import com.neo.mypatients.patient.domain.usecases.GetPatientUseCase
import com.neo.mypatients.utils.dummyPatients
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class PatientDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val repository = FakePatientRepository()

    private lateinit var getPatientUseCase: GetPatientUseCase
    private lateinit var deletePatientUseCase: DeletePatientUseCase
    private lateinit var viewModel: PatientDetailsViewModel

    @Before
    fun setUp() {
        getPatientUseCase = GetPatientUseCase(repository)
        deletePatientUseCase = DeletePatientUseCase(repository)
        viewModel = PatientDetailsViewModel(getPatientUseCase, deletePatientUseCase)
    }

    @Test
    fun `loadPatient should update state with patient details when Success`() = runTest {
        val patient = dummyPatients.first()

        viewModel.loadPatient(patient.id)
        val uiState = viewModel.uiState.value

        assertThat(patient.name).isEqualTo(uiState.patient?.name)
        assertThat(patient.age).isEqualTo(uiState.patient?.age)
        assertThat(patient.gender).isEqualTo(uiState.patient?.gender)
        assertThat(patient.phoneNumber).isEqualTo(uiState.patient?.phoneNumber)
        assertThat(patient.medicalCondition).isEqualTo(uiState.patient?.medicalCondition)
        assertThat(patient.syncStatus).isEqualTo(uiState.patient?.syncStatus)
        assertThat(uiState.loadState).isEqualTo(PatientDetailsLoadState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadPatient should update state with error and emit error event when repository returns Error`() = runTest {
        val invalidPatientId = 300L

        viewModel.uiEvent.test {
            viewModel.loadPatient(invalidPatientId)

            assertTrue(awaitItem() is PatientDetailsUiEvent.OnError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `deletePatient should emit success event when delete succeeds`() = runTest {
        val patient = dummyPatients.first()

        viewModel.uiEvent.test {
            viewModel.loadPatient(patient.id)

            viewModel.deletePatient()
            assertTrue(awaitItem() is PatientDetailsUiEvent.OnDeleteSuccess)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `deletePatient should emit error event when delete fails`() = runTest {
        val patient = dummyPatients.first().copy(id = 300L)

        viewModel.uiEvent.test {
            viewModel.loadPatient(patient.id)

            viewModel.deletePatient()
            assertTrue(awaitItem() is PatientDetailsUiEvent.OnError)
            cancelAndIgnoreRemainingEvents()
        }
    }



}