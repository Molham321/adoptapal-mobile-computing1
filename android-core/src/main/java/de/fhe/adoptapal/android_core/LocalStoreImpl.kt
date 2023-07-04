package de.fhe.adoptapal.android_core

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import de.fhe.adoptapal.domain.LocalStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalStoreImpl(val context: Context) : LocalStore {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_store")

    override suspend fun save(key: String, value: String) {
        context.dataStore.edit { prefs ->
            Log.i("LocalStore.save", "Key: $key, Value: $value")
            prefs[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun load(key: String): String {
        return context.dataStore.data.map { prefs ->
            Log.i("LocalStore.load", "Key: $key, Value: ${prefs[stringPreferencesKey(key)]}")
            prefs[stringPreferencesKey(key)]
        }.first() ?: ""
    }
}