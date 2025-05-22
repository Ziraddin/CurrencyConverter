package com.example.currencyconverter.presentation.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ApiKeyRepository(private val context: Context) {
    private val _apiKey = MutableStateFlow<String?>(null)
    val apiKey: StateFlow<String?> = _apiKey

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            fetchAndCacheApiKey()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun fetchAndCacheApiKey() {
        val key = fetchSecret(context)
        _apiKey.value = key
    }
}
