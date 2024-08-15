package com.example.koview.presentation.ui

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

// 절대경로 변환
private fun getRealPathFromUri(uri: Uri, context: Context): String? {
    return try {
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = it.getString(columnIndex)
            }
            it.close()
        }
        filePath
    } catch (e: Exception) {
        null
    }
}

// 이미지 파일로 변환
internal fun Uri.toMultiPartImage(context: Context): MultipartBody.Part? {
    getRealPathFromUri(this, context)?.let {
        val file = File(it)
        val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("images", file.name, requestFile)
    } ?: run {
        Log.e("toMultiPartImage", "Error creating multipart image")
        return null
    }
}