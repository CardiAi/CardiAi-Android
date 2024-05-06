package com.codlin.cardiai.data.datasource.remote.paging_datasource

import android.util.Log
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
        Log.d("Paging", "Getting Refresh Key: ${state.anchorPosition}")
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Patient> {
        return try {
            val currentPage = params.key ?: 1
            Log.d("Paging", "Getting Page: $currentPage")
            val response = patientService.getPatients(currentPage, searchQuery)
            Log.d("Paging", "Response: $response")
            if (response.isSuccessful) {
                val body = response.body()!!
                Log.d("Paging", "Response Body: $body")
                val patientsDto = body.data?.data ?: emptyList()
                val patients = patientsDto.map {
                    it.toDomainModel()
                }
                val backEndMetaData = body.data?.meta ?: return LoadResult.Error(NoDataException())
                Log.d("Paging", "Current Page From Backend: $backEndMetaData")
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