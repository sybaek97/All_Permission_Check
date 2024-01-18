package com.crepass.getpermissiontest.permission

import com.crepass.getpermissiontest.MainActivity

class PermissionResultProcessor(private val handler: PermissionResultHandler) {

    fun process(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        handler.onPermissionResult(requestCode, permissions, grantResults)
    }

}