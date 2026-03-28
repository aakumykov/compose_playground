package com.github.aakumykov.compose_playground.ui.navigation.older

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.aakumykov.compose_playground.ui.filter_edit.FilterEditScreen
import com.github.aakumykov.compose_playground.ui.filter_list.FilterListScreen

@Composable
fun MainNavigationOld(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavTarget.FILTER_LIST,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        //
        // Список фильтров
        //
        composable(NavTarget.FILTER_LIST) {
            FilterListScreen(
                onItemClicked = { filterId ->
                    navController.navigate("${NavTarget.FILTER_EDIT}/$filterId")
                }
            )
        }

        //
        // Правка фильтра
        //
        composable(
            route = "${NavTarget.FILTER_EDIT}/{${NavArguments.FILTER_ID}}",
            arguments = listOf(
                navArgument(NavArguments.FILTER_ID) { type = NavType.StringType },
            )
        ) {
            FilterEditScreen(
//                filterId = it.arguments?.getString(NavArguments.FILTER_ID),
                onFilterSaved = { navController.popBackStack() },
                onCancelClicked = { navController.popBackStack() }
            )
        }

        //
        // Правка фильтра
        //
        /*composable(
            route = "${NavTarget.RULE_EDIT}/{${NavArguments.FILTER_ID}}/{${NavArguments.RULE_ID}}",
            arguments = listOf(
                navArgument(NavArguments.FILTER_ID) { type = NavType.StringType },
                navArgument(NavArguments.RULE_ID) { type = NavType.StringType },
            )
        ) {
            // FIXME: убрать "!!"
            RuleEditScreen(
                filterId = it.arguments?.getString(NavArguments.FILTER_ID)!!,
                ruleId = it.arguments?.getString(NavArguments.RULE_ID),
            )
        }*/
    }
}