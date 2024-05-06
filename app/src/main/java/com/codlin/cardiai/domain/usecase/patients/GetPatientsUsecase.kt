package com.codlin.cardiai.domain.usecase.patients

import androidx.paging.PagingData
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.repo.PatientRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPatientsUsecase @Inject constructor(
    private val patientRepo: PatientRepo
) {
    operator fun invoke(searchQuery: String? = null): Flow<PagingData<Patient>> {
        return if (searchQuery.isNullOrBlank()) {
            patientRepo.getPatients()
        } else {
            patientRepo.getPatients(searchQuery)
        }
    }
}