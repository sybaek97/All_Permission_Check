package com.crepass.getpermissiontest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.crepass.getpermissiontest.databinding.ActivityMainBinding
import com.crepass.getpermissiontest.mpermission.PermissonHelper
import com.crepass.getpermissiontest.permission.PermissionResultHandler
import com.crepass.getpermissiontest.permission.PermissionResultProcessor
import com.crepass.getpermissiontest.utils.BluetoothData
import com.crepass.getpermissiontest.utils.CalendarData
import com.crepass.getpermissiontest.utils.CallLogData
import com.crepass.getpermissiontest.utils.ContactsData
import com.crepass.getpermissiontest.utils.SmsData

class MainActivity : AppCompatActivity(), PermissionResultHandler {
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionProcessor: PermissionResultProcessor

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            getSmsDataButton.setOnClickListener {
                PermissonHelper.checkSmsData(this@MainActivity)
            }
            getCallLogDataButton.setOnClickListener {
                PermissonHelper.checkCallLog(this@MainActivity)
            }
            getBluetoothDataButton.setOnClickListener {
                PermissonHelper.checkBluetooth(this@MainActivity)
            }
            getCalendarDataButton.setOnClickListener {
                PermissonHelper.checkCalendar(this@MainActivity)
            }
            getContactsDataButton.setOnClickListener {
                PermissonHelper.checkContacts(this@MainActivity)
            }
        }
        permissionProcessor = PermissionResultProcessor(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionProcessor.process(requestCode, permissions, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onPermissionResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            PermissonHelper.MY_PERMISSIONS_REQUEST_READ_SMS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 허용됨
                    SmsData().getSmsData(applicationContext)
                } else {
                    // 권한 거절됨, 사용자에게 설명을 제공
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_SMS
                        )
                    ) {
                        // 여기에 왜 이 권한이 필요한지 설명하는 대화 상자를 표시

                        showAlert(
                            "SMS 권한 필요",
                            "이 기능을 사용하려면 SMS 읽기 권한이 필요합니다."
                        )

                    } else {
                        // 사용자가 '다시 묻지 않음'을 선택하고 거절함
                        // 설정으로 유도하는 안내 메시지 표시

                        showAlert(
                            "권한 필요",
                            "설정에서 SMS 권한을 허용해주세요."
                        )

                    }

                }
                return
            }

            PermissonHelper.MY_PERMISSIONS_REQUEST_READ_CALL_LOG -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CallLogData().getCallLogData(applicationContext)
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_CALL_LOG
                        )
                    ) {
                        showAlert(
                            "Call_Log 권한 필요",
                            "이 기능을 사용하려면 Call_Log 권한이 필요합니다."
                        )

                    } else {
                        showAlert(
                            "권한 필요",
                            "설정에서 Call_Log 권한을 허용해주세요."
                        )

                    }

                }
                return
            }

            PermissonHelper.MY_PERMISSIONS_REQUEST_BLUETOOTH -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    BluetoothData().getBluetoothData(applicationContext)
                } else {

                    showAlert(
                        "권한 필요",
                        "설정에서 BLUETOOTH 권한을 허용해주세요."
                    )


                }
                return
            }

            PermissonHelper.MY_PERMISSIONS_REQUEST_CALENDAR -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CalendarData().getCalendarData(applicationContext)
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_CALL_LOG
                        )
                    ) {
                        showAlert(
                            "Calendar 권한 필요",
                            "이 기능을 사용하려면 Calendar 권한이 필요합니다."
                        )

                    } else {
                        showAlert(
                            "권한 필요",
                            "설정에서 Calendar 권한을 허용해주세요."
                        )

                    }

                }
            }
            PermissonHelper.MY_PERMISSIONS_REQUEST_CONTACTS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ContactsData().getContactsData(applicationContext)
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_CONTACTS
                        )
                    ) {
                        showAlert(
                            "CONTACTS 권한 필요",
                            "이 기능을 사용하려면 CONTACTS 권한이 필요합니다."
                        )

                    } else {
                        showAlert(
                            "권한 필요",
                            "설정에서 CONTACTS 권한을 허용해주세요."
                        )

                    }

                }
            }
        }
    }


    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("설정") { dialog, which ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("취소", null)
            .create()
            .show()
    }
}