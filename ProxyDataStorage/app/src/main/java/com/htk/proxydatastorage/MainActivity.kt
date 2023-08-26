package com.htk.proxydatastorage

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.datastore.preferences.preferencesDataStore
import com.htk.storage.ServiceStorageData
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val Context.dataStore by preferencesDataStore("user_preferences")

    private val serviceStorageData: ServiceStorageData<String> by inject()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Set your store config with name xml file
        serviceStorageData.setStoreConfig(this.dataStore)


        val button = findViewById<Button>(R.id.btnSalvar)
        button.setOnClickListener {
            serviceStorageData.save("OSVALDO AIRON BEZERRA CAVALCANTI FILHO", "userName")

            serviceStorageData.get("userName", String::class.java, onResult = {
                Log.d("Test", "OSVALDO" + it)
            })
        }

    }
}