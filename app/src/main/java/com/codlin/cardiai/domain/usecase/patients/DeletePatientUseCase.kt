package com.codlin.cardiai.domain.usecase.patients

import com.codlin.cardiai.domain.repo.PatientRepo
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePatientUseCase @Inject constructor(
    private val patientRepo: PatientRepo
) {
    operator fun invoke(patientId: Int): Flow<Resource<String>> =
        patientRepo.deletePatient(patientId)
}
