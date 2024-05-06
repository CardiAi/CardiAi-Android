package com.codlin.cardiai.data.datasource.remote.paging_datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codlin.cardiai.data.datasource.remote.service.PatientService
import com.codlin.cardiai.domain.model.Patient
import com.codlin.cardiai.domain.util.exception.NoDataException
import retrofit2.HttpException
import java.io.IOException

class PatientPagingSource(
    private val patientService: PatientService,
    private val searchQuery: String?,
) : PagingSource<Int, Patient>() {


    override fun getRefreshKey(state: PagingState<Int, Patient>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Patient> {
        return try {
            val currentPage = params.key ?: 1
            val response = patientService.getPatients(currentPage, searchQuery)
            if (response.isSuccessful) {
                val body = response.body()!!
                val patientsDto = body.data?.data ?: emptyList()
                val patients = patientsDto.map {
                    it.toDomainModel()
                }
                val backEndMetaData = body.data?.meta ?: return LoadResult.Error(NoDataException())
//                Log.d("Paging", "Meta: $backEndMetaData")
                LoadResult.Page(
                    data = patients,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (patients.isEmpty() || backEndMetaData.lastPage == backEndMetaData.currentPage) null else backEndMetaData.currentPage + 1
                )
            } else {
                LoadResult.Error(Exception("Unknown Error"))
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}