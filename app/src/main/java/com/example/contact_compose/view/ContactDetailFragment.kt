package com.example.contact_compose.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val showDialogDelete = remember { mutableStateOf(false)}

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        contact?.let {
            Column {
                GlideImage(
                    imagePath = it.avatar,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
                // name
                Text(
                    text = "Name: ${it.name}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    modifier = Modifier.padding(top = 8.dp)
                )

                // phone
                Text(
                    text = "Phone: ${it.phoneNumber}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                )

                // Email
                Text(
                    text = "Email: ${it.email}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Khoảng cách giữa các nút
            ) {

                //*********** CALL BUTTON ***********
                FloatingActionButton(onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${it.phoneNumber}")
                    }
                    navController.context.startActivity(intent)
                }) {
                    Icon(Icons.Default.Phone, contentDescription = "Call Contact")
                }

                //*********** EDIT BUTTON ***********
                FloatingActionButton(
                    onClick = { navController.navigate(route = "contactEdit/$contactId") }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Contact")
                }

                //*********** DELETE BUTTON ***********
                FloatingActionButton(
                    onClick = {
                        showDialogDelete.value = true
                    }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Contact")
                }
                //Display dialog confirm delete if press button delete
                DialogDeleteConfirmation(showDialog = showDialogDelete) {
                    viewModel.deleteContact(it)
                    navController.popBackStack() // return ContactList
                }
            }
        }
    }
}



//*********** SHOW DIALOG WHEN CLICK DELETE BUTTON ***********
@Composable
fun DialogDeleteConfirmation(showDialog: MutableState<Boolean>, onDelete: ()->Unit){
    if(showDialog.value){
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Delete Contact") },
            text = { Text("Are you sure you want to delete this contact?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDialog.value = false // change the value of showDialog -> recompose happens
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false // close dialog if choose Cancel -> recompose happens
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

