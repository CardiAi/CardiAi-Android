package com.codlin.cardiai.domain.usecase.patients

import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.repo.PatientRepo
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPatientsUsecase @Inject constructor(
    private val patientRepo: PatientRepo
) {
    operator fun invoke(page: Int, searchQuery: String? = null): Flow<Resource<List<Patient>>> {
        return if (searchQuery.isNullOrBlank()) {
            patientRepo.getPatients(page)
        } else {
            patientRepo.getPatients(page, searchQuery)
        }
    }
}