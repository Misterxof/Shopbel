package com.misterioes.shopbel.domain

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESCrypto {
    private const val SECRET_KEY = "MySuperSecretKey1234567890123456"
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"

    private val IV = "Init998877Vector3".toByteArray()

    fun encrypt(data: String): String {
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(IV))
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    fun decrypt(encryptedData: String): String {
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, IvParameterSpec(IV))
        val decodedData = Base64.decode(encryptedData, Base64.NO_WRAP)
        return String(cipher.doFinal(decodedData), Charsets.UTF_8)
    }
}