package com.krishisevak.app.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val id: String,
    val title: String,
    val timestamp: Long,
    val lastMessage: String
)

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val chatId: String,
    val sender: String, // "USER" or "AI"
    val text: String,
    val isImageAttached: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
