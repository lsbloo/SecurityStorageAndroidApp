package com.htk.storage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class ServiceStorageDataImpl<T>(
    private val encryptHandlerStorage: EncryptHandlerStorage<T>,
    private val cryptoKey: StorageInitializer<T>
) :
    ServiceStorageData<T> {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun save(data: T, key: String, onFailure: ((String) -> Unit)?) {
        CoroutineScope(Dispatchers.IO).launch {
            cryptoKey.keyCrypto?.let { encryptHandlerStorage.encryptData(data, key, cryptoKey = it) }.also {
                onFailure?.invoke("Error to obtain cryptoKey, try again")
            }
        }
    }

    override fun setStoreConfig(store: DataStore<Preferences>) {
        encryptHandlerStorage.setStoreConfig(store)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun get(key: String, clazz: Class<T>, onResult: (T?) -> Unit,onFailure: ((String) -> Unit)?) {
        cryptoKey.keyCrypto?.let { encryptHandlerStorage.decryptData(key, cryptoKey = it, clazz, onResult) }.also {
            onFailure?.invoke("Error to obtain cryptoKey, try again")
        }
    }
}