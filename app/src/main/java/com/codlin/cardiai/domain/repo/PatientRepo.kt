package com.codlin.cardiai.domain.repo

import androidx.paging.PagingData
import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PatientRepo {
    fun getPatients(searchQuery: String? = null): Flow<PagingData<Patient>>
    fun addPatient(name: String, age: Int, gender: Gender): Flow<Resource<Patient>>
    fun editPatient(patient: Patient): Flow<Resource<Patient>>
    fun deletePatient(): Flow<Resource<Unit>>
}