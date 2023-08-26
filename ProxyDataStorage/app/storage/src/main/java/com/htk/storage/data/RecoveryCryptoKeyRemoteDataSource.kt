package com.htk.storage.data

import com.htk.storage.NetworkResult
import com.htk.storage.data.remote.RecoveryCryptoKeyService

internal interface RecoveryCryptoKeyRemoteData {
    suspend fun getCryptoKey(): NetworkResult<Any>
}

internal class RecoveryCryptoKeyRemoteDataSourceImpl(private val service: RecoveryCryptoKeyService) :
    RecoveryCryptoKeyRemoteData {

    override suspend fun getCryptoKey(): NetworkResult<Any> {
        val result = service.getCryptoKey()
        if (result.isSuccessful) {
            return NetworkResult.OnSuccess(result.body())
        } else if (result.code() == 400 || result.code() == 401) {
            return NetworkResult.OnFailure("Error Http Client Request")
        } else if (result.code() >= 500) {
            return NetworkResult.OnError()
        }
        return NetworkResult.OnError()
    }

}