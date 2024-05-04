package com.codlin.cardiai.presentation.auth.signup

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.codlin.cardiai.presentation.navigation.AuthNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@AuthNavGraph
@Destination
@Composable
fun SignupScreen() {
    SignupContent()
}

@Composable
private fun SignupContent() {
    Text(text = "Signup")
}

@Preview
@Composable
private fun SignupScreenPreview() {

}