package com.neo.mypatients.patient.domain.usecases

import com.neo.mypatients.core.domain.DataError
import com.neo.mypatients.core.domain.Resource
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.patient.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow

class GetPatientsUseCase(
    private val repository: PatientRepository
) {

    operator fun invoke(
        name: String = "",
        age: Int?,
        gender: Gender?
    ): Flow<Resource<List<Patient>, DataError.Local>> {
        return repository.getPatientsByOptionalFilters(
            name = name,
            age = age,
            gender = gender
        )
    }
}