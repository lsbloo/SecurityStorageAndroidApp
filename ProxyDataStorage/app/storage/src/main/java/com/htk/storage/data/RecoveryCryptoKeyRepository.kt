package com.htk.storage.data

import com.htk.storage.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class RecoveryCryptoKeyRepository(private val recoveryCryptoKeyRemoteData: RecoveryCryptoKeyRemoteData) {
    fun getCryptoKey(onSuccess: (String) -> Unit, onFailure: (Any) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val getCryptoKey = recoveryCryptoKeyRemoteData.getCryptoKey()
            if (getCryptoKey is NetworkResult.OnSuccess) {
                getCryptoKey.value?.cryptoKey?.let { onSuccess.invoke(it) }
            } else {
                onFailure.invoke(getCryptoKey)
            }
        }
    }
}