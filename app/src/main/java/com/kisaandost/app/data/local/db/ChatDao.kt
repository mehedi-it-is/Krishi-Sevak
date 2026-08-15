package com.kisaandost.app.data.local.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Query("SELECT * FROM chats ORDER BY timestamp DESC")
    fun getAllChats(): Flow<List<ChatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(chat: ChatEntity)

    @Query("UPDATE chats SET title = :title WHERE id = :chatId")
    fun updateChatTitle(chatId: String, title: String)

    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp ASC")
    fun getMessagesForChat(chatId: String): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: MessageEntity)

    @Query("DELETE FROM chats WHERE id = :chatId")
    fun deleteChat(chatId: String): Int

    @Query("DELETE FROM chats")
    fun deleteAllChats(): Int

    @Query("DELETE FROM messages")
    fun deleteAllMessages(): Int
}
