package com.codlin.cardiai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codlin.cardiai.presentation.NavGraphs
import com.codlin.cardiai.presentation.theme.CardiAiTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private fun splashScreenSetup() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isAppReady.value
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreenSetup()

        super.onCreate(savedInstanceState)
        setContent {
            CardiAiTheme {
                val navHostEngine =
                    rememberNavHostEngine(
                        defaultAnimationsForNestedNavGraph = mapOf(
                            NavGraphs.auth to NestedNavGraphDefaultAnimations(
                                enterTransition = {
                                    slideIntoContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                        animationSpec = tween(durationMillis = 500)
                                    )
                                },
                                exitTransition = {
                                    slideOutOfContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                                        animationSpec = tween(durationMillis = 500)
                                    )
                                }
                            ),
                            NavGraphs.home to NestedNavGraphDefaultAnimations(
                                enterTransition = {
                                    slideIntoContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(durationMillis = 400)
                                    )
                                },
                                exitTransition = {
                                    slideOutOfContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                        animationSpec = tween(durationMillis = 400)
                                    )
                                },
                            )
                        ),
                    )
                val hasActiveUser by viewModel.hasActiveUser.collectAsStateWithLifecycle()
                val isAppReady by viewModel.isAppReady.collectAsStateWithLifecycle()
                if (isAppReady) {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        startRoute = if (hasActiveUser) NavGraphs.home else NavGraphs.auth,
                        engine = navHostEngine
                    )
                }
            }
        }
    }
}