package com.codlin.cardiai.presentation.new_record.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun QuestionImage(@DrawableRes resId: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = null,
        modifier = modifier
            .padding(horizontal = 70.dp)
            .aspectRatio(1f)
    )
}