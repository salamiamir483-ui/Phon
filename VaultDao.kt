package ir.privatesmsvault.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultDao {
    // ---- شماره‌های خصوصی ----
    @Query("SELECT * FROM private_numbers ORDER BY createdAt DESC")
    fun observeNumbers(): Flow<List<PrivateNumber>>

    @Query("SELECT * FROM private_numbers WHERE phoneNumber LIKE '%' || :query || '%' OR label LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchNumbers(query: String): Flow<List<PrivateNumber>>

    @Query("SELECT * FROM private_numbers WHERE id = :id")
    fun observeNumber(id: Long): Flow<PrivateNumber?>

    @Query("SELECT * FROM private_numbers WHERE phoneNumber = :phone LIMIT 1")
    suspend fun findByPhone(phone: String): PrivateNumber?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNumber(item: PrivateNumber): Long

    @Update
    suspend fun updateNumber(item: PrivateNumber)

    @Delete
    suspend fun deleteNumber(item: PrivateNumber)

    @Query("DELETE FROM private_numbers WHERE id = :id")
    suspend fun deleteNumberById(id: Long)

    // ---- رویدادها ----
    @Query("SELECT * FROM private_events ORDER BY occurredAt DESC")
    fun observeEvents(): Flow<List<PrivateEvent>>

    @Query("SELECT * FROM private_events WHERE phoneNumber = :phone ORDER BY occurredAt ASC")
    fun observeEventsFor(phone: String): Flow<List<PrivateEvent>>

    @Query("SELECT * FROM private_events WHERE phoneNumber LIKE '%' || :query || '%' OR body LIKE '%' || :query || '%' ORDER BY occurredAt DESC")
    fun searchEvents(query: String): Flow<List<PrivateEvent>>

    @Query("SELECT COUNT(*) FROM private_events WHERE isRead = 0")
    fun observeUnreadCount(): Flow<Int>

    @Query("UPDATE private_events SET isRead = 1 WHERE id = :id")
    suspend fun markEventRead(id: Long)

    @Query("UPDATE private_events SET isRead = 1 WHERE phoneNumber = :phone")
    suspend fun markThreadRead(phone: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(item: PrivateEvent): Long

    @Update
    suspend fun updateEvent(item: PrivateEvent)

    @Delete
    suspend fun deleteEvent(item: PrivateEvent)

    @Query("DELETE FROM private_events")
    suspend fun clearEvents()
}
