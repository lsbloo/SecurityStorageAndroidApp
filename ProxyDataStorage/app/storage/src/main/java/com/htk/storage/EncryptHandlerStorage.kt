package com.htk.storage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class EncryptHandlerStorage<T>(
    private val encryptStoreAlgorithm: EncryptStoreAlgorithm,
) {
    private var store: DataStore<Preferences>? = null

    fun setStoreConfig(storeConfig: DataStore<Preferences>) {
        store = storeConfig
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun encryptData(data: T, keyPreferences: String, cryptoKey: String) {
        val keyPref = stringPreferencesKey(keyPreferences)
        store?.edit { mutablePreferences ->
            mutablePreferences[keyPref] = encryptStoreAlgorithm.encrypt(data, cryptoKey)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun <T> decryptData(
        keyPreferences: String,
        cryptoKey: String,
        clazz: Class<T>,
        onResult: (T?) -> Unit
    ) {
        val keyPref = stringPreferencesKey(keyPreferences)
        CoroutineScope(Dispatchers.IO).launch {
            val valueEncrypted = store?.data?.map { preferences ->
                preferences[keyPref]
            }?.first()
            if (valueEncrypted != null) {
                onResult.invoke(encryptStoreAlgorithm.decrypt(valueEncrypted, cryptoKey, clazz))
            }
        }
    }
}
