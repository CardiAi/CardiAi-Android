package com.codlin.cardiai.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.codlin.cardiai.domain.util.exception.NoDataException
import com.codlin.cardiai.presentation.components.LazyPagingItemsError

@Composable
fun <T : Any> PaginationLazyColumn(
    pagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    loadingItem: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    content: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = contentPadding
    ) {
        items(pagingItems.itemCount) { index ->
            content(pagingItems[index]!!)
        }
        pagingItems.apply {
            when {
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

                loadState.refresh is LoadState.Error -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
                    if (error.error is NoDataException) {
                        if (itemCount == 0) {
                            item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "You have no patients added yet.",
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }
                            }
                        }
                    } else {
                        item {
                            LazyPagingItemsError(
                                errorMessage = error.error.localizedMessage
                                    ?: "An unknown error has occurred."
                            )
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