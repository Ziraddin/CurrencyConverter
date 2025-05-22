package com.example.currencyconverter.presentation.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.InputStream
import java.util.Base64

suspend fun getAccessToken(context: Context): String = withContext(Dispatchers.IO) {
    val inputStream: InputStream = context.assets.open("sa.json")
    val credentials = GoogleCredentials.fromStream(inputStream)
        .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
    credentials.refreshIfExpired()
    credentials.accessToken.tokenValue
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun fetchSecret(context: Context): String? = withContext(Dispatchers.IO) {
    try {
        val token = getAccessToken(context)

        val url =
            "https://secretmanager.googleapis.com/v1/projects/currency-converter-460605/secrets/MY_API_KEY/versions/latest:access"

        val request = Request.Builder().url(url).addHeader("Authorization", "Bearer $token").build()

        val response = OkHttpClient().newCall(request).execute()

        if (!response.isSuccessful) return@withContext null

        val responseBody = response.body?.string() ?: return@withContext null
        val json = JSONObject(responseBody)
        val base64Data = json.getJSONObject("payload").getString("data")
        String(Base64.getDecoder().decode(base64Data))

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
