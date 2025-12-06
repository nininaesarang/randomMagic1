package com.example.randommagic

import com.google.firebase.Timestamp

data class ChatMessage(
    val text: String = "",
    val userEmail: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
