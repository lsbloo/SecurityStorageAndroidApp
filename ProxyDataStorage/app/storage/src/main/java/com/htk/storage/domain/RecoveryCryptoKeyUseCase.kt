package com.htk.storage.domain

internal interface RecoveryCryptoKeyUseCase {
    fun getKeyCripto(onResult: (String) -> Unit)
}