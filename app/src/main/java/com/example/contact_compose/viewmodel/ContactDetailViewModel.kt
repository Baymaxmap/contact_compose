package com.example.contact_compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contact_compose.model.Contact
import com.example.contact_compose.model.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactDetailViewModel(private val repository: ContactRepository) : ViewModel() {
    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    fun loadContactById(contactId: Int) {
        viewModelScope.launch {
            val contactDetail = repository.getContactById(contactId)
            _contact.postValue(contactDetail)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.deleteContact(contact)
            }
        }
    }
}
