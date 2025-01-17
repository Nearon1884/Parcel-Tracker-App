package com.example.kpzparcel

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kpzparcel.ui.AddParcelForm

import com.example.kpzparcel.ui.AdminLoginForm
import com.example.kpzparcel.ui.AdminPage
import com.example.kpzparcel.ui.EditParcelForm
import com.example.kpzparcel.ui.UserLoginForm
import com.example.kpzparcel.ui.UserPage
import com.example.kpzparcel.viewmodel.ParcelViewModel

enum class ParcelNavigation(@StringRes val title: Int) {
    UserLogin(title = R.string.app_name),
    AdminLogin(title = R.string.admin_login),
    UserPage(title = R.string.user_page),
    AdminPage(title = R.string.admin_page),
    AddParcel(title = R.string.add_parcel),
    EditParcel(title = R.string.edit_parcel),
}

@Composable
fun ParcelApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Use the current route directly instead of using enum for dynamic route
//    val currentRoute = backStackEntry?.destination?.route ?: ParcelNavigation.UserLogin.name

    val currentRoute = when {
        backStackEntry?.destination?.route?.startsWith("userPage/") == true -> "Your Parcel"
        else -> backStackEntry?.destination?.route ?: ParcelNavigation.UserLogin.name
    }

    Scaffold(
        topBar = {
            ParcelAppBar(
                currentRoute = currentRoute,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = ParcelNavigation.UserLogin.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ParcelNavigation.UserLogin.name) {
                UserLoginForm(
                    LoginAdmin = {
                        navController.navigate(ParcelNavigation.AdminLogin.name)
                    },

                    PageUser = { trackingNumber ->

                        navController.navigate("userPage/$trackingNumber")
                    }
                )
            }

            composable(route =  "userPage/{trackingNumber}") { backStackEntry ->

                val trackingNumber = backStackEntry.arguments?.getString("trackingNumber") ?: ""

                UserPage(trackingNumber = trackingNumber)
            }
            composable(route = "editParcel/{trackingNumber}") { backStackEntry ->
                val trackingNumber = backStackEntry.arguments?.getString("trackingNumber") ?: ""
                val viewModel: ParcelViewModel = viewModel()

                val parcel by viewModel.getParcelByTrackingNumber(trackingNumber).observeAsState()

                parcel?.let {
                    EditParcelForm(
                        parcel = it,
                        viewModel = viewModel,
                        onEditComplete = { navController.popBackStack() } // Navigate back after saving
                    )
                } ?: run {
                    // Handle null parcel (e.g., invalid tracking number or not found)
                    Text(
                        text = "Parcel not found",

                        modifier = Modifier.fillMaxSize().padding(16.dp)
                    )
                }
            }


            composable(route = ParcelNavigation.AdminLogin.name) {
                AdminLoginForm(
                    PageAdmin = {
                        navController.navigate(ParcelNavigation.AdminPage.name)
                    }
                )
            }

            composable(route = ParcelNavigation.AdminPage.name) {
                AdminPage(
                    ParcelPage = {
                        navController.navigate(ParcelNavigation.AddParcel.name)
                    },
                    navController = navController
                )
            }


            composable(route = ParcelNavigation.AddParcel.name) {
                AddParcelForm()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelAppBar(
    currentRoute: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentRoute) },  // Use the route name directly here
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}
