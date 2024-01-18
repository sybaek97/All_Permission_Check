package com.crepass.getpermissiontest.mpermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.crepass.getpermissiontest.MainActivity
import com.crepass.getpermissiontest.utils.BluetoothData
import com.crepass.getpermissiontest.utils.CalendarData
import com.crepass.getpermissiontest.utils.CallLogData
import com.crepass.getpermissiontest.utils.ContactsData
import com.crepass.getpermissiontest.utils.SmsData

object PermissonHelper {

    const val MY_PERMISSIONS_REQUEST_READ_SMS = 1
    const val MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 2
    const val MY_PERMISSIONS_REQUEST_BLUETOOTH = 3
    const val MY_PERMISSIONS_REQUEST_CALENDAR = 4
    const val MY_PERMISSIONS_REQUEST_CONTACTS= 5

    @RequiresApi(Build.VERSION_CODES.Q)
    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
    @RequiresApi(Build.VERSION_CODES.S)
    val PERMISSIONS_S_ABOVE = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_ADVERTISE,
//        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val permissionsNeeded: MutableList<String> = ArrayList()

    /** sms권한 확인하기 (마시멜로 버전 이상부터)*/
    fun checkSmsData(activity: MainActivity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//최소버전이 28이상이라 항상 넘음 그래서 굳이 필요 xxx!!
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.READ_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(Manifest.permission.READ_SMS),
                    MY_PERMISSIONS_REQUEST_READ_SMS
                )
            } else {
                SmsData().getSmsData(activity.applicationContext)
            }
//        } else {
//            SmsData().getSmsData(activity.applicationContext)
//        }
    }

    fun checkCallLog(activity: MainActivity) {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.READ_CALL_LOG),
                MY_PERMISSIONS_REQUEST_READ_CALL_LOG
            )
        } else {
            CallLogData().getCallLogData(activity.applicationContext)
        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun checkBluetooth(activity: MainActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            for (permission in PERMISSIONS_S_ABOVE) {
                if (ContextCompat.checkSelfPermission(
                        activity,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsNeeded.add(permission)
                }
            }
            if(!permissionsNeeded.isNullOrEmpty()){
                ActivityCompat.requestPermissions(activity, permissionsNeeded.toTypedArray(),MY_PERMISSIONS_REQUEST_BLUETOOTH)
            }else{
                BluetoothData().getBluetoothData(activity.applicationContext)
            }
        }else{
            for(permission in PERMISSIONS){
                if (ContextCompat.checkSelfPermission(
                        activity,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsNeeded.add(permission)
                }
            }
            if(!permissionsNeeded.isNullOrEmpty()){
                ActivityCompat.requestPermissions(activity, permissionsNeeded.toTypedArray(),MY_PERMISSIONS_REQUEST_BLUETOOTH)
            }else{
                BluetoothData().getBluetoothData(activity.applicationContext)
            }

        }

    }

    fun checkCalendar(activity: MainActivity){
        if(ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_CALENDAR
        )!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.READ_CALENDAR),
                MY_PERMISSIONS_REQUEST_CALENDAR
            )
        }else{
            CalendarData().getCalendarData(activity.applicationContext)
        }
    }

    fun checkContacts(activity: MainActivity){
        if(ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_CONTACTS
            )!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.READ_CONTACTS),
                MY_PERMISSIONS_REQUEST_CONTACTS
            )
        }else{
            ContactsData().getContactsData(activity.applicationContext)

        }
    }
}