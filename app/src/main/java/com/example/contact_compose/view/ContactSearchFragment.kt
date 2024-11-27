package com.example.contact_compose.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.contact_compose.viewmodel.ContactListViewModel

@Composable
fun ContactSearchScreen(navController: NavHostController, viewModel: ContactListViewModel) {
    var query by remember { mutableStateOf("") }
    val filteredContacts by viewModel.filteredContacts.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.searchContacts(query)
            },
            label = { Text("Search Contact") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(filteredContacts) { contact ->
                ContactItem(contact = contact, onClick = {
                    navController.navigate("contactDetail/${contact.id}")
                })
            }
        }
    }
}
