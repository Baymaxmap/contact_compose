package com.example.contact_compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact_compose.model.Contact
import com.example.contact_compose.model.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactListViewModel(private val _repository: ContactRepository): ViewModel() {
    //attributes for display list of contact on UI
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>>
        get() = _contacts

    //attribute to search a specific contact
    private val _filteredContacts = MutableLiveData<List<Contact>>() // Filtered contacts
    val filteredContacts: LiveData<List<Contact>>
        get() = _filteredContacts

    init {
        fetchContacts()
    }

    //************ FETCH CONTACTS FROM DATABASE TO LIVEDATA ATTRIBUTE************
    fun fetchContacts(){
        viewModelScope.launch {
            val listContact = withContext(Dispatchers.IO) {
                _repository.getAllContacts()
            }
            _contacts.postValue(listContact)
            //_filteredContacts.postValue(listContact)
        }
    }

    //search a specific contact
    fun searchContacts(query: String) {
        val currentContacts = _contacts.value ?: emptyList()
        _filteredContacts.value = if (query.isBlank()) {
            currentContacts // Show all if query is empty
        } else {
            currentContacts.filter { contact ->
                contact.name.contains(query, ignoreCase = true) ||
                        contact.phoneNumber.contains(query, ignoreCase = true) ||
                        contact.email.contains(query, ignoreCase = true)
            }
        }
    }
}