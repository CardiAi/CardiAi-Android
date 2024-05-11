package com.codlin.cardiai.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.codlin.cardiai.domain.util.exception.NoDataException
import com.codlin.cardiai.presentation.components.LazyPagingItemsError
import timber.log.Timber

@Composable
fun <T : Any> PaginationLazyColumn(
    pagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    loadingItem: (@Composable () -> Unit)? = null,
    emptyListComposable: (@Composable LazyItemScope.() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    refresh: Boolean = false,
    content: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = contentPadding
    ) {
        pagingItems.apply {
            when {
                loadState.refresh is LoadState.Error -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
                    if (error.error is NoDataException) {
                        item {
                            if (emptyListComposable != null) {
                                emptyListComposable()
                            }
                        }
                    } else {
                        item {
                            LazyPagingItemsError(
                                errorMessage = "Unable to connect to the server."
                            )
                        }
                    }
                }

                loadState.append is LoadState.NotLoading -> {
                    items(pagingItems.itemCount) { index ->
                        content(pagingItems[index]!!)
                        LaunchedEffect(key1 = refresh) {
                            if (refresh) {
                                pagingItems.refresh()
                            }
                        }
                        LaunchedEffect(key1 = pagingItems.itemCount) {
                            Timber.d("Paging Count: ${pagingItems.itemCount}")
                        }
                    }
                }

                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    loadingItem?.let {
                        item {
                            loadingItem()
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        LazyPagingItemsError(
                            errorMessage = "Unable to load more data."
                        )
                    }
                }
            }
        }
    }
}