package com.codlin.cardiai.data.datasource.remote.dto.base

data class BasePaginationResponse<T>(
    val data: List<T>?,
    val meta: PaginationMeta?,
)