package com.codlin.cardiai.presentation.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class AuthNavGraph(
    val start: Boolean = false,
)