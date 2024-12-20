package com.example.contact_compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact_compose.model.Contact
import com.example.contact_compose.model.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactEditViewModel @Inject constructor(private val repository: ContactRepository) : ViewModel() {
    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact> = _contact

    fun loadContactById(contactId: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                repository.getContactById(contactId)
            }
            _contact.postValue(result)
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.updateContact(contact)
            }
        }
    }
}
