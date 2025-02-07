package com.example.contact_compose.application

import android.app.Application
import com.example.contact_compose.model.Contact
import com.example.contact_compose.model.database.AppDatabase
import com.example.contact_compose.model.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactApp : Application() {
    private lateinit var _database: AppDatabase
    lateinit var contactRepository: ContactRepository

    override fun onCreate() {
        super.onCreate()
        _database = AppDatabase.getDatabase(this)
        contactRepository = ContactRepository(_database.contactDao())

        // Initialize data only if it's the first run
        if (isFirstRun()) {
            initializeContacts()
            setFirstRunComplete()
        }
    }

    private fun initializeContacts() {
        // Since this involves Room operations, use a coroutine to handle it properly
        CoroutineScope(Dispatchers.IO).launch {
            contactRepository.insertContact(Contact(name = "Thanh1", phoneNumber = "123", email = "thanh1@", avatar = "/storage/emulated/0/Download/avt.jpg"))
            contactRepository.insertContact(Contact(name = "Thanh2", phoneNumber = "234", email = "thanh2@", avatar = "/storage/emulated/0/Download/avt1.jpg"))
            contactRepository.insertContact(Contact(name = "Thanh3", phoneNumber = "345", email = "thanh3@", avatar = "/storage/emulated/0/Download/avt2.jpg"))
            contactRepository.insertContact(Contact(name = "Thanh4", phoneNumber = "456", email = "thanh4@", avatar = "/storage/emulated/0/Download/avt3.jpg"))
            contactRepository.insertContact(Contact(name = "Thanh5", phoneNumber = "567", email = "thanh5@", avatar = "/storage/emulated/0/Download/avt4.png"))
            contactRepository.insertContact(Contact(name = "Thanh6", phoneNumber = "678", email = "thanh6@", avatar = "/storage/emulated/0/Download/avt5.png"))
            contactRepository.insertContact(Contact(name = "Thanh7", phoneNumber = "789", email = "thanh7@", avatar = "/storage/emulated/0/Download/avt6.jpg"))
        }
    }

    private fun isFirstRun(): Boolean {
        val prefs = getSharedPreferences("app_preferences", MODE_PRIVATE)
        return prefs.getBoolean("is_first_run", true)
    }

    private fun setFirstRunComplete() {
        val prefs = getSharedPreferences("app_preferences", MODE_PRIVATE)
        prefs.edit().putBoolean("is_first_run", false).apply()
    }
}