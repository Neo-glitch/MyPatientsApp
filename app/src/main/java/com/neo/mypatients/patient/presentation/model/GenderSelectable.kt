package com.neo.mypatients.patient.presentation.model

import com.neo.mypatients.core.presentation.model.IDropdownSelectable
import com.neo.mypatients.patient.data.datasources.local.model.Gender

data class GenderSelectable(
    val gender: Gender,
): IDropdownSelectable {

    override fun getLabel(): String {
        return gender.name
    }

    companion object {
        val GENDERS = Gender.entries.map { GenderSelectable(it) }
    }
}
