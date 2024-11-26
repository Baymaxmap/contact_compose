package com.example.contact_compose

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contact_compose.application.ContactApp
import com.example.contact_compose.ui.theme.Contact_composeTheme
import com.example.contact_compose.view.ContactNavHost
import com.example.contact_compose.viewmodel.factory.ContactViewModelFactory

class MainActivity : ComponentActivity() {
    private val REQUEST_CODE = 1001
    private val app by lazy { application as ContactApp }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestStoragePermission()
        val factory = ContactViewModelFactory(app.contactRepository)
        setContent {
            //Contact_composeTheme {
                MainScreen(factory)
        }
    }

    // Xử lý kết quả yêu cầu quyền
    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    REQUEST_CODE
                )
            }
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(factory: ContactViewModelFactory){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            //check if NavHost display which fragment?
            when(navController.currentDestination?.route){}
            TopAppBar(
                title = { Text(text = "Contacts")},
                actions = {
                    IconButton(onClick = {/* click search icon */}) {
                        Icon(painter = painterResource(R.drawable.icon_search),
                            contentDescription = "Search contact"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /*icon menu clicked*/ }) {
                        Icon(painter = painterResource(R.drawable.icon_menu),
                            contentDescription = "Menu contact"
                        )
                    }
                }
            )
        }
    ) {paddingValues ->  
        Box(modifier = Modifier.padding(paddingValues)){
            ContactNavHost(navController, viewModelFactory = factory)
        }
    }
}

