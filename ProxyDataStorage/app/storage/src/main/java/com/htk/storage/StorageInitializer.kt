package com.htk.storage

import com.htk.storage.domain.RecoveryCryptoKeyUseCase
import kotlinx.coroutines.runBlocking

internal class StorageInitializer<T>(private val recoveryCryptoKeyUseCase: RecoveryCryptoKeyUseCase) {
    var keyCrypto: String? = null
    init {
        runBlocking {
            recoveryCryptoKeyUseCase.getKeyCripto {
                keyCrypto = it
            }
        }
    }
}
