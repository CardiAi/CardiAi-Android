package com.codlin.cardiai.domain.repo

import androidx.paging.PagingData
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PatientRepo {
    fun getPatients(searchQuery: String? = null): Flow<PagingData<Patient>>
    fun addPatient(patient: Patient): Flow<Resource<Patient>>
    fun editPatient(patient: Patient): Flow<Resource<Patient>>
    fun deletePatient(patientId: Int): Flow<Resource<String>>
}