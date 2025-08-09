package com.neo.mypatients.patient.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neo.mypatients.R
import com.neo.mypatients.patient.data.datasources.local.model.Gender
import com.neo.mypatients.patient.data.datasources.local.model.SyncStatus
import com.neo.mypatients.patient.domain.model.Patient
import com.neo.mypatients.ui.theme.MyPatientsTheme

@Composable
fun PatientItem(modifier: Modifier = Modifier, patient: Patient, onItemClick: (Patient) -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = {
            onItemClick(patient)
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape),
                painter = painterResource(R.drawable.ic_patient),
                contentDescription = "Patient Image",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            PatientInfo(patient = patient)
        }
    }
}

@Composable
private fun PatientInfo(patient: Patient) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = patient.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val genderIcon = if (patient.gender == Gender.Male) R.drawable.ic_male_icon else R.drawable.ic_female_icon
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(genderIcon),
                contentDescription = "Gender icon"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = patient.gender.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            maxLines = 1,
            text = stringResource(R.string.x_years_label, patient.age.toString()),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true, apiLevel = 34, showSystemUi = true)
@Composable
private fun PatientItemCardPreview() {
    MyPatientsTheme {
        Surface {
            PatientItem(
                patient = Patient(
                    name = "John Doe",
                    age = 30,
                    gender = Gender.Male,
                    phoneNumber = "123-456-7890",
                    medicalCondition = "Hypertension",
                    syncStatus = SyncStatus.SYNCED,
                    id = 1,
                ),
                onItemClick = {}
            )
        }
    }
}
