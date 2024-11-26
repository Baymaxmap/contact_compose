package com.example.contact_compose.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contact_compose.viewmodel.ContactDetailViewModel
import com.example.contact_compose.viewmodel.ContactListViewModel
import com.example.contact_compose.viewmodel.factory.ContactViewModelFactory

@Composable
fun ContactNavHost(navController: NavController, viewModelFactory: ContactViewModelFactory) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "contactList") {
        composable("contactList") {
            val viewModel: ContactListViewModel = viewModel(factory = viewModelFactory)
            ContactListScreen(navController, viewModel)
        }
        composable(
            "contactDetail/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: 0
            val viewModel: ContactDetailViewModel = viewModel(factory = viewModelFactory)
            ContactDetailScreen(viewModel, contactId)
        }
    }
}