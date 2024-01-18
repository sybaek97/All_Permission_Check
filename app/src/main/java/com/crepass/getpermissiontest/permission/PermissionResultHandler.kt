package com.crepass.getpermissiontest.permission

interface PermissionResultHandler {

    fun onPermissionResult(requestCode:Int,permissions:Array<String>,grantResults: IntArray)

}