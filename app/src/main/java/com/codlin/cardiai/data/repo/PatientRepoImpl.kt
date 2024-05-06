package com.codlin.cardiai.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codlin.cardiai.data.datasource.remote.paging_datasource.PatientPagingSource
import com.codlin.cardiai.data.datasource.remote.service.PatientService
import com.codlin.cardiai.domain.model.Gender
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.repo.PatientRepo
import com.codlin.cardiai.domain.util.Resource
import kotlinx.coroutines.flow.Flow
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
//    override fun getPatients(page: Int, searchQuery: String?): Flow<Resource<List<Patient>>> =
//        flow {
//            emit(Resource.Loading())
//            val patientsDtoList = tryRequest {
//                val response = patientService.getPatients(page, searchQuery)
//                if (response.isSuccessful) {
//                    val body = response.body()!!
//                    body.data!!.data
//                } else {
//                    null
//                }
//            }
//            val patientList = patientsDtoList?.let { list ->
//                list.map {
//                    it.toDomainModel()
//                }
//            }
//            emit(Resource.Success(patientList))
//        }

    override fun addPatient(name: String, age: Int, gender: Gender): Flow<Resource<Patient>> {
        TODO("Not yet implemented")
    }

    override fun editPatient(patient: Patient): Flow<Resource<Patient>> {
        TODO("Not yet implemented")
    }

    override fun deletePatient(): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

}