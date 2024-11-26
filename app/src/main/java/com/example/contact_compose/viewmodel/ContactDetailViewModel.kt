package com.example.contact_compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact_compose.model.Contact
import com.example.contact_compose.model.repository.ContactRepository
import kotlinx.coroutines.launch

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
}
