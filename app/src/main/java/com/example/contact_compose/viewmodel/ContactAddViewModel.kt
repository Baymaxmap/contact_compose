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
class ContactAddViewModel @Inject constructor(private val repository: ContactRepository) : ViewModel() {
    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    fun insertContact(contact: Contact){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insertContact(contact)
            }
        }
    }
}
