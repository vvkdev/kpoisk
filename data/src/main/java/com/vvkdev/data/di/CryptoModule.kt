package com.vvkdev.data.di

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.vvkdev.data.constants.PrefsConstants
import com.vvkdev.data.crypto.CryptoService
import com.vvkdev.data.crypto.CryptoServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CryptoBindModule {

    @Binds
    fun bindCryptoService(impl: CryptoServiceImpl): CryptoService
}

@Module
@InstallIn(SingletonComponent::class)
object CryptoProvideModule {

    private const val KEYSET_NAME = "apikey_keyset"
    private const val MASTER_KEY_URI = "android-keystore://kpoisk_masterkey"

    @Provides
    @Singleton
    fun provideAead(@ApplicationContext context: Context): Aead {
        AeadConfig.register()
        return AndroidKeysetManager.Builder()
            .withSharedPref(context, KEYSET_NAME, PrefsConstants.PREF_NAME)
            .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
            .getPrimitive(RegistryConfiguration.get(), Aead::class.java)
    }
}
