package com.htk.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface ServiceStorageData<T> {
    fun setStoreConfig(store: DataStore<Preferences>)
    fun save(data: T, key: String,onFailure: ((String) -> Unit) ? = null)
    fun get(key: String, clazz: Class<T>, onResult: (T?) -> Unit,onFailure: ((String) -> Unit) ? = null)
}