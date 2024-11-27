package com.example.contact_compose

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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

        //request permission to access media images
        requestStoragePermission()
        val factory = ContactViewModelFactory(app.contactRepository)
        setContent {
            MainScreen(factory)
        }
    }

    // handle request permission
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

    // Get NavBackStackEntry
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Get current route
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        topBar = {
            //check NavHost display which fragment? -> display topBar corresponding with that
            when(currentRoute){
                //ContactListScreen if NavHost display list
                "contactList" ->
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

                //ContactDetailScreen if NavHost display detail contact
                "contactDetail/{contactId}" ->
                    TopAppBar(
                        title = { Text(text = "Contact Detail")},
                        navigationIcon = {
                            IconButton(onClick = { navController.navigate(route = "contactList") }) {
                                Icon(painter = painterResource(R.drawable.icon_back),
                                    contentDescription = "Menu contact"
                                )
                            }
                        }
                    )

                //ContactEditScreen if NavHost display edit contact
                "contactEdit/{contactId}" ->
                    TopAppBar(
                        title = { Text(text = "Contact Edit")},
                        navigationIcon = {
                            IconButton(onClick = {
//                                val contactId = navBackStackEntry?.arguments?.getInt("contactId") ?: 0
//                                navController.navigate(route = "contactDetail/$contactId")
                                navController.popBackStack()
                            }) {
                                Icon(painter = painterResource(R.drawable.icon_back),
                                    contentDescription = ""
                                )
                            }
                        }
                    )

                //ContactAddScreen if NavHost display add contact
                "contactAdd" ->
                    TopAppBar(
                        title = { Text(text = "Contact Add")},
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.popBackStack()    //Back to contactList
                            }) {
                                Icon(painter = painterResource(R.drawable.icon_back),
                                    contentDescription = ""
                                )
                            }
                        }
                    )
            }
        }
    ) {paddingValues ->
        //Box to contain NavHost (list of contacts, detail contact, add contact, edit contact)
        Box(modifier = Modifier.padding(paddingValues)){
            ContactNavHost(navController = navController, viewModelFactory = factory)
        }
    }
}

