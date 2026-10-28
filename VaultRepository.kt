package ir.privatesmsvault.data

import kotlinx.coroutines.flow.Flow

/**
 * لایهٔ Repository: تنها نقطهٔ دسترسی UI به داده‌ها.
 * داده‌ها کاملاً محلی‌اند؛ هیچ ارسال شبکه‌ای وجود ندارد.
 */
class VaultRepository(private val dao: VaultDao) {

    fun numbers(query: String = ""): Flow<List<PrivateNumber>> =
        if (query.isBlank()) dao.observeNumbers() else dao.searchNumbers(query.trim())

    fun number(id: Long): Flow<PrivateNumber?> = dao.observeNumber(id)

    fun events(query: String = ""): Flow<List<PrivateEvent>> =
        if (query.isBlank()) dao.observeEvents() else dao.searchEvents(query.trim())

    fun thread(phoneNumber: String): Flow<List<PrivateEvent>> = dao.observeEventsFor(phoneNumber)

    fun unreadCount(): Flow<Int> = dao.observeUnreadCount()

    suspend fun saveNumber(phoneNumber: String, label: String, existingId: Long = 0) {
        val normalized = phoneNumber.trim()
        require(normalized.isNotEmpty()) { "شماره نمی‌تواند خالی باشد" }
        val existing = dao.findByPhone(normalized)
        if (existingId == 0L) {
            if (existing == null) dao.addNumber(PrivateNumber(phoneNumber = normalized, label = label.trim()))
            else dao.updateNumber(existing.copy(label = label.trim()))
        } else dao.updateNumber(PrivateNumber(id = existingId, phoneNumber = normalized, label = label.trim()))
    }

    suspend fun deleteNumber(id: Long) = dao.deleteNumberById(id)

    suspend fun addEvent(item: PrivateEvent): Long = dao.addEvent(item)
    suspend fun markEventRead(id: Long) = dao.markEventRead(id)
    suspend fun markThreadRead(phone: String) = dao.markThreadRead(phone)
    suspend fun deleteEvent(item: PrivateEvent) = dao.deleteEvent(item)
    suspend fun clearEvents() = dao.clearEvents()
}
