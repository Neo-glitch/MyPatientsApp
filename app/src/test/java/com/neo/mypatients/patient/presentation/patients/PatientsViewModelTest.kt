package com.neo.mypatients.patient.presentation.patients

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.neo.mypatients.MainDispatcherRule
import com.neo.mypatients.patient.FakePatientRepository
import com.neo.mypatients.patient.domain.usecases.GetPatientsUseCase
import com.neo.mypatients.utils.dummyPatients
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class PatientsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val repository = FakePatientRepository()
    private lateinit var getPatientsUseCase : GetPatientsUseCase
    private lateinit var viewModel: PatientsViewModel

    @Before
    fun setUp() {
        getPatientsUseCase = GetPatientsUseCase(repository)
        viewModel = PatientsViewModel(getPatientsUseCase)
    }

    @Test
    fun `initial load emits Loading then Success`() = runTest {
        viewModel.uiState.test {
            val firstUiStateSnapShot = awaitItem()
            assertThat(firstUiStateSnapShot.loadState).isEqualTo(PatientsLoadState.Loading)

            val secondUiStateSnapShot = awaitItem()
            assertThat(secondUiStateSnapShot.loadState).isEqualTo(PatientsLoadState.Success)
            assertThat(secondUiStateSnapShot.patients).isEqualTo(dummyPatients)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `query update updates the query field in the Ui state`() = runTest {
        val query = "John"
        viewModel.onQueryUpdate(query)
        val uiState = viewModel.uiState.value
        assertThat(uiState.query).isEqualTo(query)
    }

    @Test
    fun `onQueryUpdate filters patients by name`() = runTest {
        val query = dummyPatients.first().name.substring(0, 3)
        viewModel.onQueryUpdate(query)
        val uiState = viewModel.uiState.value
        assertTrue(uiState.patients.all { it.name.contains(query, ignoreCase = true) })
    }

}