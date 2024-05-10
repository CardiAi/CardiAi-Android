package com.codlin.cardiai.data.datasource.remote.paging_datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codlin.cardiai.data.datasource.remote.service.RecordService
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.domain.util.exception.NoDataException
import retrofit2.HttpException
import java.io.IOException

class RecordPagingSource(
    private val recordService: RecordService,
    private val patientId: Int,
) : PagingSource<Int, Record>() {
    override fun getRefreshKey(state: PagingState<Int, Record>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
        return try {
            val currentPage = params.key ?: 1
            val response =
                recordService.getPatientRecords(patientId = patientId, page = currentPage)
            if (response.isSuccessful) {
                val body = response.body()!!
                val recordsDto = body.data?.data ?: emptyList()
                val records = recordsDto.map {
                    it.toDomainModel()
                }
                val backEndMetaData = body.data?.meta ?: return LoadResult.Error(NoDataException())
                LoadResult.Page(
                    data = records,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (records.isEmpty() || backEndMetaData.lastPage == backEndMetaData.currentPage) null else backEndMetaData.currentPage + 1
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