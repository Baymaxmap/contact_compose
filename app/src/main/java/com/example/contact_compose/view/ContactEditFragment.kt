package com.example.contact_compose.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.contact_compose.viewmodel.ContactEditViewModel

@Composable
fun ContactEditScreen(navController: NavHostController, viewModel: ContactEditViewModel, contactId: Int) {
    LaunchedEffect(contactId) {
        viewModel.loadContactById(contactId)
    }

    val contact by viewModel.contact.observeAsState()

    // Tạo các biến trạng thái cho thông tin liên hệ
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var avatar by remember { mutableStateOf("") }

    // fetch contact and display on UI
    LaunchedEffect(contact) {
        contact?.let {
            name = it.name
            phoneNumber = it.phoneNumber
            email = it.email
            avatar = it.avatar
        }
    }

    // Khởi tạo ActivityResultLauncher để chọn ảnh
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                avatar = it.toString() // Cập nhật đường dẫn ảnh vào trạng thái
            }
        }
    )

    Box(modifier = Modifier.padding(16.dp)) {
        contact?.let { originalContact ->
            Column(modifier = Modifier.padding(16.dp)) {

                //display avatar
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { launcher.launch("image/*") }
                ) {
                    GlideImage(
                        imagePath = avatar,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                //enter value into text field
                OutlinedTextField(
                    value = name,
                    onValueChange = { input -> name = input },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { input -> phoneNumber = input },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { input -> email = input },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                //SAVE CONTACT
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // Tạo đối tượng Contact mới và truyền vào updateContact
                    val updatedContact = originalContact.copy(
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email,
                        avatar = avatar
                    )
                    viewModel.updateContact(updatedContact)
                    navController.popBackStack() // Quay lại màn hình trước
                }) {
                    Text("Save Contact")
                }
            }
        } ?: Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
    }
}
