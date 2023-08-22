package com.example.experiencesampleapp.ui.page

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.experiencesampleapp.entity.Page
import com.example.experiencesampleapp.viewmodel.TestViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainFramework(
    testViewModel: TestViewModel,
    application: Application
) {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navigationItems = listOf(Page.Home)

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.padding(5.dp),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
//                    if (currentDestination?.route != Page.Home.route) {
//                        IconButton(
//                            onClick = { navController.navigateUp() }
//                        ) { Icon(Icons.Filled.ArrowBack, null) }
//                    }
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) { Icon(Icons.Filled.ArrowBack, null) }
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            navController.navigate(Page.Settings.route) {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
                navigationItems.forEachIndexed { index, navigationItem ->
                    BottomNavigationItem(
                        icon = {
                            when (index) {
                                0 -> Icon(Icons.Filled.Face, contentDescription = null)
                                else -> Icon(Icons.Filled.DateRange, contentDescription = null)
                            }
                        },
                        label = { Text(stringResource(navigationItem.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == navigationItem.route } == true,
                        selectedContentColor = Color(0xFF4552B8),
                        unselectedContentColor = Color.Gray,
                        onClick = {
                            navController.navigate(navigationItem.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }

        },
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
        ) {
            NavHost(
                navController = navController,
                startDestination = Page.Home.route
            ) {
                composable(Page.Home.route) {
                    HomePage(
                        testViewModel = testViewModel,
                    )
                }
                composable(Page.Settings.route) {
                    SettingPage(
                        testViewModel = testViewModel,
                        application = application
                    )
                }
            }
        }
    }
}