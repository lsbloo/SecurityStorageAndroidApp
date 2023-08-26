package com.htk.storage.di

import com.htk.storage.data.remote.RecoveryCryptoKeyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StorageNetworkModule {

    val storageNetworkModule = module {
        factory {
            setConfigRetrofit()
        }
        factory {
            cryptoRecoveryService(get())
        }
    }

    private fun setConfigRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)

        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient.build())
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    private fun cryptoRecoveryService(retrofit: Retrofit) =
        retrofit.create(RecoveryCryptoKeyService::class.java)

    //Mock server
    private const val BASE_URL = "http://private-cfad10-proxyapi5.apiary-mock.com/"
}