package main

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Timer
import java.util.TimerTask

fun main() {
    val client = OkHttpClient()
    val serverUrl = "%SERVER_IP%" // Change this to your server URL

    // Function to send a request to the server
    fun sendIpToServer() {
        val request = Request.Builder()
            .url(serverUrl)
            .build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                println("Request sent successfully")
            } else {
                println("Failed to send request: ${response.message}")
            }
        }

    }

    // Timer to send request periodically
    Timer().scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            sendIpToServer()
        }
    }, 0, 60 * 60 * 1000) // Send every minute
}