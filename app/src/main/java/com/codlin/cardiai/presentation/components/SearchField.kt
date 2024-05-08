package com.codlin.cardiai.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.codlin.cardiai.presentation.theme.Primary1000

@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onCancelClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier,
    cancelable: Boolean = true,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .shadow(
                2.dp,
                shape = RoundedCornerShape(64.dp),
                ambientColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        singleLine = true,
        shape = RoundedCornerShape(64.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = Primary1000
            )
        },
        placeholder = {
            Text(
                text = "Search...",
                modifier = Modifier.alpha(0.7f),
                color = Primary1000
            )
        },
        trailingIcon = {
            if (cancelable) {
                IconButton(onClick = onCancelClicked) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "Cancel",
                        tint = Primary1000
                    )
                }
            }
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked()
                keyboardController?.hide()
            },
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchFieldPreview() {
    CardiAiTheme {
        SearchField(
            "",
            {},
            {},
            {},
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .padding(4.dp)
        )
    }
}