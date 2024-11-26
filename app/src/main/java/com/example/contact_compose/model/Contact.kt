package com.example.contact_compose.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
    var phoneNumber: String = "",
    var email: String = "",
    var avatar: String = DEFAULT_PATH
) : Parcelable {
    companion object {
        const val DEFAULT_PATH = "/storage/emulated/0/Download/default_avt.png"
    }
}