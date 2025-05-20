package com.vvkdev.data.crypto

import com.google.crypto.tink.Aead
import com.google.crypto.tink.subtle.Base64
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoServiceImpl @Inject constructor(
    private val aead: Aead,
) : CryptoService {

    override fun encrypt(text: String): String =
        Base64.encode(aead.encrypt(text.toByteArray(), null))

    override fun decrypt(encryptedText: String): String =
        String(aead.decrypt(Base64.decode(encryptedText), null))
}
