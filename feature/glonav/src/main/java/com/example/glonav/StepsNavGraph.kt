package com.example.glonav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dailycounter.presentation.DailyCounterRoute
import com.example.profile.presentation.MoreRoute
import com.example.profile.presentation.MoreSettingsRoute
import com.example.statistic.presentation.StatisticRoute
import com.example.utils.Navigation

@Composable
fun StepsNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Navigation.DAILY_COUNTER.route,
        modifier = modifier
    ) {
        composable(
            route = Navigation.DAILY_COUNTER.route
        ) {
            DailyCounterRoute(navController)
        }
        composable(
            route = Navigation.STATISTICS.route
        ) {
            StatisticRoute(navController)
        }
        composable(
            route = Navigation.MORE.route,
            enterTransition = { enterTransition(SlideDirection.Left) },
            exitTransition = { exitTransition(SlideDirection.Left) },
            popEnterTransition = { enterTransition(SlideDirection.Right) },
            popExitTransition = { exitTransition(SlideDirection.Right) }
        ) {
            MoreRoute(navController)
        }
        composable(
            route = Navigation.MORE_SETTINGS.route,
            enterTransition = { enterTransition(SlideDirection.Left) },
            exitTransition = { exitTransition(SlideDirection.Left) },
            popEnterTransition = { enterTransition(SlideDirection.Right) },
            popExitTransition = { exitTransition(SlideDirection.Right) }
        ) {
            MoreSettingsRoute(navController)
        }
    }

}


private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(direction: SlideDirection) =
    fadeOut(
        animationSpec = tween(
            durationMillis = ANIMATION_DURATION,
            easing = LinearEasing
        )
    ) + slideOutOfContainer(
        towards = direction,
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = EaseOut)
    )


private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(direction: SlideDirection) =
    fadeIn(
        animationSpec = tween(
            durationMillis = ANIMATION_DURATION,
            easing = LinearEasing
        )
    ) + slideIntoContainer(
        towards = direction,
        animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = EaseIn)
    )

private const val ANIMATION_DURATION = 500