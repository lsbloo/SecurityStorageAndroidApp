package com.htk.storage.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.htk.storage.EncryptHandlerStorage
import com.htk.storage.EncryptStoreAlgorithm
import com.htk.storage.ServiceStorageData
import com.htk.storage.ServiceStorageDataImpl
import com.htk.storage.StorageInitializer
import com.htk.storage.data.RecoveryCryptoKeyRemoteData
import com.htk.storage.data.RecoveryCryptoKeyRemoteDataSourceImpl
import com.htk.storage.data.RecoveryCryptoKeyRepository
import com.htk.storage.data.remote.RecoveryCryptoKeyService
import com.htk.storage.domain.RecoveryCryptoKeyUseCase
import com.htk.storage.domain.RecoveryCryptoKeyUseCaseImpl
import org.koin.dsl.module

object StorageModule {

    @RequiresApi(Build.VERSION_CODES.O)
    val storageModule = module {
        factory {
            provideRemoteDataSourceRecoveryCryptoKey(get())
        }
        factory {
            provideRecoveryCryptoKeyRepository(get())
        }
        factory {
            provideRecoveryCryptoKeyUseCase(get())
        }
        factory {
            provideEncryptHandlerStorage<Any>(EncryptStoreAlgorithm())
        }
        factory { StorageInitializer<Any>(get()) }

        factory {
            provideServiceStorageData<Any>(get(), get())
        }
    }

    private fun provideRemoteDataSourceRecoveryCryptoKey(service: RecoveryCryptoKeyService): RecoveryCryptoKeyRemoteData {
        return RecoveryCryptoKeyRemoteDataSourceImpl(service)
    }

    private fun provideRecoveryCryptoKeyRepository(recoveryCryptoKeyRemoteDataSource: RecoveryCryptoKeyRemoteData): RecoveryCryptoKeyRepository {
        return RecoveryCryptoKeyRepository(recoveryCryptoKeyRemoteDataSource)
    }

    private fun provideRecoveryCryptoKeyUseCase(recoveryCryptoKeyRepository: RecoveryCryptoKeyRepository): RecoveryCryptoKeyUseCase {
        return RecoveryCryptoKeyUseCaseImpl(recoveryCryptoKeyRepository)
    }

    private fun <T> provideEncryptHandlerStorage(
        context: EncryptStoreAlgorithm
    ): EncryptHandlerStorage<T> {
        return EncryptHandlerStorage(context)
    }

    private fun <T> provideServiceStorageData(
        handler: EncryptHandlerStorage<T>,
        cryptoKey: StorageInitializer<T>
    ): ServiceStorageData<T> {
        return ServiceStorageDataImpl(handler, cryptoKey)
    }

}