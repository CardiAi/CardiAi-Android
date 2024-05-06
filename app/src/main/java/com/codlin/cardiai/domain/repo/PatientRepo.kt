package com.codlin.cardiai.domain.repo

import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PatientRepo {
    fun getPatients(page: Int, searchQuery: String? = null): Flow<Resource<List<Patient>>>
    fun addPatient(name: String, age: Int, gender: Gender): Flow<Resource<Patient>>
    fun editPatient(patient: Patient): Flow<Resource<Patient>>
    fun deletePatient(): Flow<Resource<Unit>>
}