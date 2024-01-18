package com.crepass.getpermissiontest.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.crepass.getpermissiontest.MainActivity
import com.crepass.getpermissiontest.mpermission.PermissonHelper
import com.google.gson.JsonArray
import com.google.gson.JsonObject


class BluetoothData {

    val TAG: String = BluetoothData::class.java.simpleName

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getBluetoothData(context: Context) {

        Log.d(TAG, "성공")

        val bluetoothManager: BluetoothManager by lazy {
            context.getSystemService(BluetoothManager::class.java)
        }
        val bluetoothAdapter: BluetoothAdapter? by lazy {
            bluetoothManager.adapter
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            for (permission in PermissonHelper.PERMISSIONS_S_ABOVE) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    PermissonHelper.permissionsNeeded.add(permission)
                }
            }
            //TODO
            val bluetoothStatus = if (bluetoothAdapter?.isEnabled == true) "on" else "Off"
            val bluetoothName = bluetoothAdapter?.name
            // 디바이스 Mac주소 (일반적으로 접근 불가)
            val bluetoothAddress = bluetoothAdapter?.address
            // 페어링된 기기 목록
            val pairedDevices = bluetoothAdapter?.bondedDevices
            val bluetoothDeviceJsonArray = JsonArray()

            pairedDevices?.forEach { bluetoothDevice ->
                // 각 페어링된 기기에 대한 정보
                val bluetoothDeviceJsonObject = JsonObject()
                bluetoothDeviceJsonObject.addProperty("name", bluetoothDevice.name)
                bluetoothDeviceJsonObject.addProperty("address", bluetoothDevice.address)
                bluetoothDeviceJsonObject.addProperty("device_class", bluetoothDevice.bluetoothClass.deviceClass)
                bluetoothDeviceJsonObject.addProperty("device_major_class", bluetoothDevice.bluetoothClass.majorDeviceClass)
                bluetoothDeviceJsonObject.addProperty("bond_state", bluetoothDevice.bondState)
                if (Build.VERSION.SDK_INT >= 18) {
                    bluetoothDeviceJsonObject.addProperty("type", bluetoothDevice.type)
                }
                bluetoothDeviceJsonArray.add(bluetoothDeviceJsonObject)
                // 여기에 정보 출력 또는 처리 코드 추가
            }
            // 기타 지원 여부 확인
            val isLe2MPhySupported = bluetoothAdapter?.isLe2MPhySupported
            val isLeCodedPhySupported = bluetoothAdapter?.isLeCodedPhySupported
            val isLeExtendedAdvertisingSupported =
                bluetoothAdapter?.isLeExtendedAdvertisingSupported
            val isLePeriodicAdvertisingSupported =
                bluetoothAdapter?.isLePeriodicAdvertisingSupported
            val isOffloadedFilteringSupported = bluetoothAdapter?.isOffloadedFilteringSupported
            val isOffloadedScanBatchingSupported =
                bluetoothAdapter?.isOffloadedScanBatchingSupported
            val isMultipleAdvertisementSupported =
                bluetoothAdapter?.isMultipleAdvertisementSupported
            val leMaximumAdvertisingDataLength = bluetoothAdapter?.leMaximumAdvertisingDataLength

            // 여기에 정보 출력 또는 처리 코드 추가
            Log.d("bluetoothStatus", bluetoothStatus)
            Log.d("bluetoothName", bluetoothName.toString())
            Log.d("bluetoothAddress", bluetoothAddress.toString())
            Log.d("pairedDevices", bluetoothDeviceJsonArray.toString())
            Log.d("isLe2MPhySupported", isLe2MPhySupported.toString())
            Log.d("isLeCodedPhySupported", isLeCodedPhySupported.toString())
            Log.d("isLeExtendedAdvertisingSupported", isLeExtendedAdvertisingSupported.toString())
            Log.d("isLePeriodicAdvertisingSupported", isLePeriodicAdvertisingSupported.toString())
            Log.d("isOffloadedFilteringSupported", isOffloadedFilteringSupported.toString())
            Log.d("isOffloadedScanBatchingSupported", isOffloadedScanBatchingSupported.toString())
            Log.d("isMultipleAdvertisementSupported", isMultipleAdvertisementSupported.toString())
            Log.d("leMaximumAdvertisingDataLength", leMaximumAdvertisingDataLength.toString())

        } else {
            for (permission in PermissonHelper.PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    PermissonHelper.permissionsNeeded.add(permission)
                }
            }

            //TODO
            val bluetoothStatus = if (bluetoothAdapter?.isEnabled == true) "on" else "Off"
            val bluetoothName = bluetoothAdapter?.name
            // 디바이스 Mac주소 (일반적으로 접근 불가)
            val bluetoothAddress = bluetoothAdapter?.address
            // 페어링된 기기 목록
            val pairedDevices = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                // 각 페어링된 기기에 대한 정보
                val deviceName = device.name
                val deviceAddress = device.address
                val deviceType = device.type
                val deviceClass = device.bluetoothClass.deviceClass
                val deviceMajorClass = device.bluetoothClass.majorDeviceClass
                // 여기에 정보 출력 또는 처리 코드 추가
            }
            // 기타 지원 여부 확인
            val isLe2MPhySupported = bluetoothAdapter?.isLe2MPhySupported
            val isLeCodedPhySupported = bluetoothAdapter?.isLeCodedPhySupported
            val isLeExtendedAdvertisingSupported =
                bluetoothAdapter?.isLeExtendedAdvertisingSupported
            val isLePeriodicAdvertisingSupported =
                bluetoothAdapter?.isLePeriodicAdvertisingSupported
            val isOffloadedFilteringSupported = bluetoothAdapter?.isOffloadedFilteringSupported
            val isOffloadedScanBatchingSupported =
                bluetoothAdapter?.isOffloadedScanBatchingSupported
            val isMultipleAdvertisementSupported =
                bluetoothAdapter?.isMultipleAdvertisementSupported
            val leMaximumAdvertisingDataLength = bluetoothAdapter?.leMaximumAdvertisingDataLength

            // 여기에 정보 출력 또는 처리 코드 추가
            Log.d("bluetoothStatus", bluetoothStatus)
            Log.d("bluetoothName", bluetoothName.toString())
            Log.d("bluetoothAddress", bluetoothAddress.toString())
            Log.d("pairedDevices", pairedDevices.toString())
            Log.d("isLe2MPhySupported", isLe2MPhySupported.toString())
            Log.d("isLeCodedPhySupported", isLeCodedPhySupported.toString())
            Log.d("isLeExtendedAdvertisingSupported", isLeExtendedAdvertisingSupported.toString())
            Log.d("isLePeriodicAdvertisingSupported", isLePeriodicAdvertisingSupported.toString())
            Log.d("isOffloadedFilteringSupported", isOffloadedFilteringSupported.toString())
            Log.d("isOffloadedScanBatchingSupported", isOffloadedScanBatchingSupported.toString())
            Log.d("isMultipleAdvertisementSupported", isMultipleAdvertisementSupported.toString())
            Log.d("leMaximumAdvertisingDataLength", leMaximumAdvertisingDataLength.toString())


        }


    }

}