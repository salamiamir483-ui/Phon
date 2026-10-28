package ir.privatesmsvault.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** شمارهٔ خصوصی ذخیره‌شده روی دستگاه. */
@Entity(tableName = "private_numbers")
data class PrivateNumber(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phoneNumber: String,
    val label: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

/** رویداد تماس یا پیامک مرتبط با یک شمارهٔ خصوصی. */
@Entity(tableName = "private_events")
data class PrivateEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phoneNumber: String,
    val eventType: String,           // CALL یا SMS
    val direction: String = "IN",    // IN یا OUT
    val body: String = "",           // تماس: خالی؛ پیامک: متن محلی
    val occurredAt: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
