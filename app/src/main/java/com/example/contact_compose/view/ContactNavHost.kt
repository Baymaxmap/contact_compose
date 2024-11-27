package com.example.contact_compose.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.contact_compose.viewmodel.ContactAddViewModel
import com.example.contact_compose.viewmodel.ContactDetailViewModel
import com.example.contact_compose.viewmodel.ContactEditViewModel
import com.example.contact_compose.viewmodel.ContactListViewModel
import com.example.contact_compose.viewmodel.factory.ContactViewModelFactory

@Composable
fun ContactNavHost(navController: NavHostController, viewModelFactory: ContactViewModelFactory) {

    NavHost(navController, startDestination = "contactList") {

        //ContactListScreen begin
        composable("contactList") {
            val viewModel: ContactListViewModel = viewModel(factory = viewModelFactory)
            ContactListScreen(navController, viewModel)
        }

        //ContactDetailScreen
        composable(
            "contactDetail/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: 0
            val viewModel: ContactDetailViewModel = viewModel(factory = viewModelFactory)
            ContactDetailScreen(navController,viewModel, contactId)
        }

        //ContactEditScreen
        composable(
            "contactEdit/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: 0
            val viewModel: ContactEditViewModel = viewModel(factory = viewModelFactory)
            ContactEditScreen(navController, viewModel, contactId)
        }

        //ContactAddScreen
        composable("contactAdd") {
            val viewModel: ContactAddViewModel = viewModel(factory = viewModelFactory)
            ContactAddScreen(navController, viewModel)
        }
    }
}