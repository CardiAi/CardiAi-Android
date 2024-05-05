package com.codlin.cardiai.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {
    companion object Keys {
        private val accessTokenKey = stringPreferencesKey("access_token")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = token
        }
    }

    suspend fun getUserToken(): String {
        val token =
            context.dataStore.data.first()[accessTokenKey] ?: throw Exception("Unauthorized")
        return "Bearer $token"
    }

    suspend fun isTokenAvailable(): Boolean {
        try {
            getUserToken()
            return true
        } catch (_: Exception) {
            return false
        }
    }
}