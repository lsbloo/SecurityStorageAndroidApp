package com.htk.storage.domain

import com.htk.storage.data.RecoveryCryptoKeyRepository

internal class RecoveryCryptoKeyUseCaseImpl(private val recoveryCryptoKeyRepository: RecoveryCryptoKeyRepository) :
    RecoveryCryptoKeyUseCase {
    override fun getKeyCripto(onResult: (String) -> Unit)  {
        recoveryCryptoKeyRepository.getCryptoKey(onSuccess = { key ->
            onResult.invoke(key)
            return@getCryptoKey
        }, onFailure = {})
    }
}