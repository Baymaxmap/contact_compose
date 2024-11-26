package com.example.contact_compose.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.contact_compose.viewmodel.ContactDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactDetailScreen(navController: NavHostController ,viewModel: ContactDetailViewModel, contactId: Int) {
    LaunchedEffect(contactId) {
        viewModel.loadContactById(contactId)
    }
    val contact by viewModel.contact.observeAsState()

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        contact?.let {
            Column {
                GlideImage(
                    imagePath = it.avatar,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Text(text = "Name: ${it.name}")
                Text(text = "Phone: ${it.phoneNumber}")
                Text(text = "Email: ${it.email}")
            }
        } ?: Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))

        // Floating Action Button navigate to Contact Edit
        FloatingActionButton(
            //navigate to ContactEdit and argument is the ID of contact
            onClick = { navController.navigate(route = "contactEdit/$contactId")},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Contact")
        }
    }
}
