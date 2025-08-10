package com.neo.mypatients.patient.presentation.add_edit_patient

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.neo.mypatients.MainDispatcherRule
import com.neo.mypatients.patient.FakePatientRepository
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.domain.usecases.AddEditPatientUseCase
import com.neo.mypatients.patient.domain.usecases.GetPatientUseCase
import com.neo.mypatients.utils.dummyPatients
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertTrue

class AddEditPatientViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val repository = FakePatientRepository()

    private lateinit var addEditPatientUseCase: AddEditPatientUseCase
    private lateinit var getPatientUseCase: GetPatientUseCase
    private lateinit var viewModel: AddEditPatientViewModel

    @Before
    fun setUp() {
        addEditPatientUseCase = AddEditPatientUseCase(repository)
        getPatientUseCase = GetPatientUseCase(repository)
        viewModel = AddEditPatientViewModel(addEditPatientUseCase, getPatientUseCase)
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
        assertThat(uiState.loadState).isEqualTo(AddEditPatientLoadState.Idle)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadPatient should update state with error and emit error event when repository returns Error`() = runTest {
        val invalidPatientId = 300L

        viewModel.uiEvent.test {
            viewModel.loadPatient(invalidPatientId)

            val uiState = viewModel.uiState.value
            assertThat(uiState.loadState).isEqualTo(AddEditPatientLoadState.Error)

            assertTrue(awaitItem() is AddEditPatientUiEvent.OnError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `savePatientDetails should emit success event when add succeeds`() = runTest {
        viewModel.uiEvent.test {
            viewModel.updateName("John Doe")
            viewModel.updateAge("30")
            viewModel.updateGender(Gender.Male)
            viewModel.updatePhoneNumber("08012345678")
            viewModel.updateMedicalCondition("Hypertension")

            viewModel.savePatientDetails()

            val uiState = viewModel.uiState.value
            assertThat(uiState.loadState).isEqualTo(AddEditPatientLoadState.Idle)

            assertTrue(awaitItem() is AddEditPatientUiEvent.OnSuccess)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `savePatientDetails should emit error event when add fails`() = runTest {

        viewModel.uiEvent.test {
            viewModel.updateName("John Doe")
            viewModel.updateAge("500")
            viewModel.updateGender(Gender.Male)
            viewModel.updatePhoneNumber("08012345678")
            viewModel.updateMedicalCondition("Hypertension")

            viewModel.savePatientDetails()

            val uiState = viewModel.uiState.value
            assertThat(uiState.loadState).isEqualTo(AddEditPatientLoadState.Error)

            assertTrue(awaitItem() is AddEditPatientUiEvent.OnError)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `updateName should change state name`() {
        viewModel.updateName("Jane Smith")
        assertThat("Jane Smith").isEqualTo(viewModel.uiState.value.name)
    }

    @Test
    fun `updateAge should change state age`() {
        viewModel.updateAge("45")
        assertThat("45").isEqualTo(viewModel.uiState.value.age)
    }

    @Test
    fun `updateGender should change state gender`() {
        viewModel.updateGender(Gender.Female)
        assertThat(Gender.Female).isEqualTo(viewModel.uiState.value.gender)
    }

    @Test
    fun `updatePhoneNumber should change state phoneNumber`() {
        viewModel.updatePhoneNumber("08099999999")
        assertThat("08099999999").isEqualTo(viewModel.uiState.value.phoneNumber)
    }

    @Test
    fun `updateMedicalCondition should change state medicalCondition`() {
        viewModel.updateMedicalCondition("Asthma")
        assertThat("Asthma").isEqualTo(viewModel.uiState.value.medicalCondition)
    }

}