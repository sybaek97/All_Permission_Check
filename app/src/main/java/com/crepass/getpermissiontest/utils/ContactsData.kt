package com.crepass.getpermissiontest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import android.util.Log

class ContactsData {


    val TAG: String = ContactsData::class.java.simpleName
    @SuppressLint("Range")
    fun getContactsData(context: Context) {
        Log.d(TAG, "콜로그 들어옴")
        val contacts = mutableListOf<Contact>()

        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    val phones = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )

                    var phoneNumber: String? = null
                    phones?.use {
                        if (it.moveToFirst()) {
                            phoneNumber = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        }
                    }

                    contacts.add(Contact(name, phoneNumber))
                } while (it.moveToNext())
            }
        }
        Log.d("연락처", contacts.toString())

    }

    data class Contact(
        val name: String?,
        val phoneNumber: String?
    )
}