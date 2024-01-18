package com.crepass.getpermissiontest.utils

import android.content.Context
import android.net.Uri
import android.util.Log

class SmsData {
    val TAG: String = SmsData::class.java.simpleName
    fun getSmsData(context: Context){
        Log.e(TAG,"성공")
        val smsUri= Uri.parse("content://sms/inbox")
        val cursor=context.contentResolver.query(smsUri,null,null,null,null)

        cursor?.let {
            val indexBody=it.getColumnIndex("body")
            val indexAddress=it.getColumnIndex("address")
            while(it.moveToNext()){
                val smsBody=it.getString(indexBody)
                val smsAddresses=it.getString(indexAddress)

                Log.e("문자 ",smsBody)
                Log.e("주소 ",smsAddresses)

            }
        it.close()
        }

    }

}