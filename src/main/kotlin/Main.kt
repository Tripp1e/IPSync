package main

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Timer
import java.util.TimerTask
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val client = OkHttpClient()
    val serverUrl = "%SERVER_ADDRESS%" // Change this to your server URL

    // Start the Ktor server
    embeddedServer(Netty, port = 25566) {
        routing {
            get("/log_ip") {
                // Execute the Bash command when the request is received
                launch(Dispatchers.IO) {
                    executeBashCommand()
                }
                call.respondText("Command executed", ContentType.Text.Plain)
            }
        }
    }.start(wait = false) // Start the server in the background

    // Timer to send request periodically
    Timer().scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            sendIpToServer(client, serverUrl)
        }
    }, 0, 60 * 60 * 1000) // Send every hour
}

// Function to execute a Bash command
fun executeBashCommand() {
    try {
        val process = Runtime.getRuntime().exec("echo Command Executed")
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        reader.forEachLine { println(it) }
        process.waitFor()
    } catch (e: Exception) {
        println("Error executing command: ${e.message}")
    }
}

// Function to send a request to the server
fun sendIpToServer(client: OkHttpClient, serverUrl: String) {
    val request = Request.Builder()
        .url(serverUrl)
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            println(response)
            println("Request sent successfully")
        } else {
            println("Failed to send request: ${response.message}")
        }
    }
}
