package com.pres.common.permissions

interface PermissionsListener {
    /**
     * 同意权限申请
     */
    fun onGranted(permissions: MutableList<String>)

    /**
     * 拒绝权限申请
     */
    fun onDenied(permissions: MutableList<String>)

    /**
     * 拒绝权限，并且不再询问
     */
    fun onNeverAgain(permissions: MutableList<String>)
}
