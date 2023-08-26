package com.htk.proxydatastorage

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.htk.storage.di.StorageModule
import com.htk.storage.di.StorageNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class ProxyDataStorageApp : Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ProxyDataStorageApp)
            modules(getModules())
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getModules() =
        listOf<Module>(StorageNetworkModule.storageNetworkModule, StorageModule.storageModule)

}