package com.codlin.cardiai.domain.usecase.patients

import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.repo.PatientRepo
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditPatientUseCase @Inject constructor(private val patientRepo: PatientRepo) {
    operator fun invoke(patient: Patient): Flow<Resource<Patient>> =
        patientRepo.editPatient(patient)
}