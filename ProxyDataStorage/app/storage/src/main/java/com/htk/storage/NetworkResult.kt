package com.htk.storage

import com.htk.storage.data.model.CryptoKeyResponseDTO

internal sealed class NetworkResult<out T : Any> {
    data class OnSuccess<out T : Any>(val value: CryptoKeyResponseDTO?) : NetworkResult<T>()
    data class OnFailure<out T : Any>(val value: T) : NetworkResult<T>()
    data class OnError<out T : Any>(val value: NetworkException = NetworkException()) : NetworkResult<T>()
}

internal class NetworkException : Exception()