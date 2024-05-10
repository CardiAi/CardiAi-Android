package com.codlin.cardiai.presentation.home.record_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codlin.cardiai.R
import com.codlin.cardiai.domain.model.record.Record
import com.codlin.cardiai.presentation.UIFormatter
import com.codlin.cardiai.presentation.components.RecordIcon
import com.codlin.cardiai.presentation.home.record_details.components.RecordValueItem
import com.codlin.cardiai.presentation.navigation.HomeNavGraph
import com.codlin.cardiai.presentation.theme.Neutral500
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.reflect.full.declaredMemberProperties


@HomeNavGraph
@Destination(navArgsDelegate = Record::class)
@Composable
fun RecordDetailsScreen(
    navigator: DestinationsNavigator,
    record: Record,
) {
    val viewModel =
        hiltViewModel<RecordDetailsViewModel, RecordDetailsViewModel.RecordDetailsViewModelFactory>() {
            it.create(record)
        }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.navigateUp) {
        if (state.navigateUp) {
            navigator.popBackStack()
            viewModel.resetEvents()
        }
    }

    RecordDetailsContent(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecordDetailsContent(
    state: RecordDetailsState,
    onEvent: (RecordDetailsEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onEvent(RecordDetailsEvent.OnBackClicked) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Arrow Back Icon",
                        )
                    }
                },
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                RecordIcon(
                    result = state.record.result,
                    modifierBackground = Modifier.size(98.dp),
                    modifierHeart = Modifier.size(49.dp)
                )
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = state.record.patientName!!,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black,
                    )
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = UIFormatter.formatRecordResult(state.record.result),
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.Black,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Spacer(modifier = Modifier.width(80.dp))
                        Text(
                            text = state.record.createdAt ?: "",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Black,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                color = Neutral500,
            )
            Spacer(modifier = Modifier.height(24.dp))
            for (prop in state.record::class.declaredMemberProperties) {
                val value = prop.getter.call(state.record) ?: "Not Recorded"
                val name = prop.name
                if (
                    name.contains("patient") ||
                    name.contains("id") ||
                    name.contains("At")
                ) continue
                RecordValueItem(
                    name = name,
                    value = value.toString(),
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}