package com.example.base.lib

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    @Singleton
    @Named(ENCRYPTED_PREFERENCES_NAME)
    fun provideEncryptedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val keySpec = KeyGenParameterSpec
                .Builder(MasterKey.DEFAULT_MASTER_KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT and KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                .build()

        val masterKeyAlias = MasterKey
                .Builder(context)
                .setKeyGenParameterSpec(keySpec)
                .build()

        return EncryptedSharedPreferences.create(
                context,
                "crypto",
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        const val ENCRYPTED_PREFERENCES_NAME = "ENCRYPTED_PREFERENCES_NAME"
        const val BASIC_PREFERENCES_NAME = "BASIC_PREFERENCES_NAME"
    }
}
