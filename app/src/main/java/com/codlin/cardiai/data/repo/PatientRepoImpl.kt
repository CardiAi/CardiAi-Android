package com.codlin.cardiai.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codlin.cardiai.data.datasource.remote.dto.patients.PatientDto
import com.codlin.cardiai.data.datasource.remote.paging_datasource.PatientPagingSource
import com.codlin.cardiai.data.datasource.remote.service.PatientService
import com.codlin.cardiai.data.datasource.remote.util.tryRequest
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.repo.PatientRepo
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PatientRepoImpl @Inject constructor(private val patientService: PatientService) :
    PatientRepo {
    override fun getPatients(searchQuery: String?): Flow<PagingData<Patient>> =
        Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = {
                PatientPagingSource(patientService, searchQuery)
            }
        ).flow

    override fun addPatient(patient: Patient): Flow<Resource<Patient>> = flow {
        emit(Resource.Loading())
        val addedPatient: Patient? = tryRequest {
            val response =
                patientService.addPatient(PatientDto.fromDomainModel(patient))
            if (response.isSuccessful) {
                val body = response.body()!!
                body.data!!.toDomainModel()
            } else {
                emit(Resource.Error(Exception(response.body()?.message)))
                null
            }
        }
        addedPatient?.let {
            emit(Resource.Success(it))
        }
    }

    override fun editPatient(patient: Patient): Flow<Resource<Patient>> = flow {
        emit(Resource.Loading())
        val resultPatient: Patient? = tryRequest {
            val response =
                patientService.editPatient(patient.id!!, PatientDto.fromDomainModel(patient))
            if (response.isSuccessful) {
                val body = response.body()!!
                body.data!!.toDomainModel()
            } else {
                emit(Resource.Error(Exception(response.body()?.message)))
                null
            }
        }
        resultPatient?.let {
            emit(Resource.Success(it))
        }
    }

    override fun deletePatient(patientId: Int): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        tryRequest {
            val response = patientService.deletePatient(patientId)
            if (response.isSuccessful) {
                emit(Resource.Success("Deleted patient successfully!"))
            } else {
                emit(Resource.Error(Exception(response.body()?.message)))
            }
        }
    }

}