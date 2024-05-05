package com.codlin.cardiai.data.datasource.remote.dto.base

import com.google.gson.annotations.SerializedName

data class PaginationMeta(
    val currentPage: Int,
    @SerializedName("from")
    val allPagesCount: Int,
    @SerializedName("per_page")
    val itemsPerPage: Int,
    @SerializedName("total")
    val totalItems: Int,
)
