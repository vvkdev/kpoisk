package com.vvkdev.data.crypto

interface CryptoService {
    fun encrypt(text: String): String
    fun decrypt(encryptedText: String): String
}
