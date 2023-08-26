package com.htk.storage.data.model

import com.google.gson.annotations.SerializedName

data class CryptoKeyResponseDTO(
    @SerializedName("cryptoKey")
    var cryptoKey: String? = null
)
