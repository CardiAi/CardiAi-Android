package com.codlin.cardiai.domain.repo

import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient

interface PatientRepo {
    fun startDiagnosis()
    fun addPatient(name: String, age: Int, gender: Gender): Result<Patient>
    fun editPatient(age: Int): Result<Patient>
    fun deletePatient(): Result<Unit>
}