package ir.privatesmsvault.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PrivateNumber::class, PrivateEvent::class], version = 2, exportSchema = false)
abstract class VaultDatabase : RoomDatabase() {
    abstract fun vaultDao(): VaultDao

    companion object {
        @Volatile private var instance: VaultDatabase? = null

        fun get(context: Context): VaultDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, VaultDatabase::class.java, "vault.db")
                .fallbackToDestructiveMigration() // نسخهٔ ۱ فقط نمونه‌داده داشت؛ ارتقا مخرب کافی است
                .build().also { instance = it }
        }
    }
}
