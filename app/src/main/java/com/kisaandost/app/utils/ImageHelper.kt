package com.kisaandost.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageHelper {
    fun compressAndEncodeToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitmap == null) return null

            var quality = 100
            var outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

            // Target max size ~1MB
            while (outputStream.toByteArray().size > 1000000 && quality > 10) {
                quality -= 10
                outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            }

            Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun compressBitmapToBase64(bitmap: Bitmap): String? {
        return try {
            var quality = 100
            var outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

            while (outputStream.toByteArray().size > 1000000 && quality > 10) {
                quality -= 10
                outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            }

            Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun bitmapToCacheUri(context: Context, bitmap: Bitmap): Uri? {
        return try {
            val file = java.io.File(context.cacheDir, "crop_cam_${System.currentTimeMillis()}.jpg")
            val fos = java.io.FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.flush()
            fos.close()
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
