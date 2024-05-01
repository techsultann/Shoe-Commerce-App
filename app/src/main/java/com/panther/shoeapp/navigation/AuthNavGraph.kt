package com.panther.shoeapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.auth.LoginScreen
import com.panther.shoeapp.ui.presentation.auth.OnboardingViewModel
import com.panther.shoeapp.ui.presentation.auth.SignupScreen
import com.panther.shoeapp.ui.presentation.auth.forgot_password.ForgotPassword
import com.panther.shoeapp.ui.presentation.auth.forgot_password.ForgotPasswordPass
import com.panther.shoeapp.ui.presentation.onboard.OnboardScreen
import com.panther.shoeapp.ui.presentation.splash.SplashScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {

    navigation(
        startDestination = AuthScreen.SplashScreen.route,
        route = Graph.AUTHENTICATION
    ) {
        composable(route = AuthScreen.SplashScreen.route){
            SplashScreen(navHostController = navController)
        }
        composable(
            route = AuthScreen.OnboardScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){
            OnboardScreen(navController = navController)
        }
        composable(
            route = AuthScreen.SignupScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            val vm = hiltViewModel<OnboardingViewModel>()
            SignupScreen(navController = navController, vm)
        }
        composable(
            route = AuthScreen.LoginScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            val vm = hiltViewModel<OnboardingViewModel>()
            LoginScreen(navController = navController, vm)
        }
        composable(
            route = AuthScreen.ForgotPasswordEmail.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            val vm = hiltViewModel<OnboardingViewModel>()
            ForgotPassword(navController = navController)
        }
        composable(
            route = AuthScreen.ForgotPassword.route ,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            val vm = hiltViewModel<OnboardingViewModel>()
            ForgotPasswordPass(navController)
        }
    }
}

sealed class AuthScreen(val route: String) {
    object OnboardScreen : AuthScreen(route = "onboard_screen")
    object SignupScreen : AuthScreen(route = "signup_screen")
    object LoginScreen : AuthScreen (route = "login_screen")
    object ForgotPasswordEmail : AuthScreen (route = "forgot_pass_email")
    object ForgotPassword : AuthScreen (route = "forgot_pass")
    object SplashScreen : AuthScreen (route = "splash_screen")
}