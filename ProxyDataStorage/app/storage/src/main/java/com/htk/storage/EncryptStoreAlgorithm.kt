package com.htk.storage

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey

import javax.crypto.spec.SecretKeySpec


@RequiresApi(Build.VERSION_CODES.O)
internal class EncryptStoreAlgorithm {
    private var initVector = ByteArray(16)

    init {
        SecureRandom().nextBytes(initVector)
    }

    fun <T> encrypt(data: T, cryptoKey: String): String {
        val dataString = Gson().toJson(data)
        val cipher = Cipher.getInstance("AES/ECB/PKCS7PADDING")
        try {
            val secret: SecretKey = SecretKeySpec(cryptoKey.toByteArray(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret)
            val bytesCrypt = cipher.doFinal(dataString.toByteArray(charset = Charsets.UTF_8))
            return Base64.getEncoder().encodeToString(bytesCrypt)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun <T> decrypt(data: String, cryptoKey: String, clazz: Class<T>): T {
        val cypherText = Base64.getDecoder().decode(data)

        val cipher = Cipher.getInstance("AES/ECB/PKCS7PADDING")
        val secret: SecretKey = SecretKeySpec(cryptoKey.toByteArray(), "AES")

        cipher.init(Cipher.DECRYPT_MODE, secret)

        val decrypt = cipher.doFinal(cypherText)
        val stringDecrypt = Base64.getEncoder().encodeToString(decrypt)
        val result = String(
            Base64.getDecoder().decode(stringDecrypt),
            charset = Charsets.UTF_8
        )

        return Gson().fromJson(result, clazz)
    }
}