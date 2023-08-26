package com.htk.storage.data.remote

import com.htk.storage.data.model.CryptoKeyResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface RecoveryCryptoKeyService {

    @GET("api/recovery/key")
    suspend fun getCryptoKey(): Response<CryptoKeyResponseDTO>
}