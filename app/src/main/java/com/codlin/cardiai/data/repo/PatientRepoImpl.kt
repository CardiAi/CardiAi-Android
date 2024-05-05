package com.codlin.cardiai.data.repo

import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.repo.PatientRepo
import javax.inject.Inject

class PatientRepoImpl @Inject constructor(/*private val apiService: ApiService*/) : PatientRepo {
    override fun startDiagnosis() {

    }

    override fun addPatient(name: String, age: Int, gender: Gender): Result<Patient> {
        return Result.success(Patient())
    }

    override fun editPatient(age: Int): Result<Patient> {
        return Result.success(Patient())
    }

    override fun deletePatient(): Result<Unit> {
        return Result.success(Unit)
    }

}