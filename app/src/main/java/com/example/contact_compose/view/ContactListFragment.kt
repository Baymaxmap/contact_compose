package com.example.contact_compose.view

import android.widget.ImageView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.bumptech.glide.Glide
import com.example.contact_compose.R
import com.example.contact_compose.model.Contact
import com.example.contact_compose.viewmodel.ContactListViewModel

@Composable
fun ContactListScreen(
    navController: NavHostController,
    viewModel: ContactListViewModel
) {
    val contacts by viewModel.contacts.observeAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        // Recycler view
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(contacts) { contact ->
                ContactItem(contact = contact, onClick = {
                    navController.navigate("contactDetail/${contact.id}")
                })
            }
        }

        // Floating Action Button navigate to Contact Add
        FloatingActionButton(
            onClick = { navController.navigate("contactAdd") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Contact")
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hiển thị ảnh đại diện bằng Glide
        GlideImage(
            imagePath = contact.avatar,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Hiển thị tên liên hệ
        Text(
            text = contact.name,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun GlideImage(
    imagePath: String?,
    modifier: Modifier = Modifier,
    placeholder: Int = R.drawable.icon_avatar_background,
    error: Int = R.drawable.icon_avatar_background
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            if (!imagePath.isNullOrEmpty()) {
                Glide.with(imageView.context)
                    .load(imagePath)
                    .circleCrop()
                    .placeholder(placeholder)
                    .error(error)
                    .into(imageView)
            } else {
                imageView.setImageResource(placeholder)
            }
        }
    )
}

