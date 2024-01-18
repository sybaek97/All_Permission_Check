package com.crepass.getpermissiontest.utils

import android.content.Context
import android.provider.CallLog
import android.util.Log


class CallLogData {

    val TAG: String = CallLogData::class.java.simpleName
    fun getCallLogData(context: Context){
        Log.d(TAG, "콜로그 들어옴")

        val callSet = arrayOf(
            CallLog.Calls.DATE,
            CallLog.Calls.TYPE,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DURATION
        )
        val cursor =
            context.contentResolver.query(CallLog.Calls.CONTENT_URI, callSet, null, null, null)

        cursor?.let {
            var returnData = ArrayList<Any>()
            val number = cursor.getColumnIndex(CallLog.Calls.NUMBER)//폰번호
            val type = cursor.getColumnIndex(CallLog.Calls.TYPE)//폰번호
            val date = cursor.getColumnIndex(CallLog.Calls.DATE)//폰번호
            val duration = cursor.getColumnIndex(CallLog.Calls.DURATION)//폰번호

            while (it.moveToNext()) {
                val getNumber = cursor.getString(number)
                val getType = cursor.getString(type)
                val getDate = cursor.getString(date)

                val getDuration = cursor.getString(duration)

                var dir: String? = null
                val dircode: Int = getType.toInt()
                when (dircode) {
                    CallLog.Calls.OUTGOING_TYPE -> dir = "발신"
                    CallLog.Calls.INCOMING_TYPE -> dir = "수신"
                    CallLog.Calls.MISSED_TYPE -> dir = "부재중"
                    CallLog.Calls.REJECTED_TYPE -> dir = "종료"
                }
                val map = HashMap<String, Any>()
                map["PhoneNumber"] = java.lang.String.valueOf(getNumber)
                map["CallType"] = java.lang.String.valueOf(dir)
                map["CallDate"] = java.lang.String.valueOf(getDate)
                map["CallDurationSec"] = java.lang.String.valueOf(getDuration)
                returnData.add(map)
            }
            Log.d("통화 목록", returnData.toString())
        }


    }

}