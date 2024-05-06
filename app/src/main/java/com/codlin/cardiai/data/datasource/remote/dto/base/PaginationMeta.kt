package com.codlin.cardiai.data.datasource.remote.dto.base

import com.google.gson.annotations.SerializedName

data class PaginationMeta(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("per_page")
    val itemsPerPage: Int,
    @SerializedName("total")
    val totalItems: Int,
)
