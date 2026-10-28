package ir.privatesmsvault.vault

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/** PIN ciphertext is stored in app-private SharedPreferences; AES key never leaves Android Keystore. */
class PinVault(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("vault_pin", Context.MODE_PRIVATE)
    private val alias = "private_sms_vault_pin_aes"
    private val transformation = "AES/GCM/NoPadding"

    private fun key(): SecretKey {
        val store = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        (store.getKey(alias, null) as? SecretKey)?.let { return it }
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        generator.init(KeyGenParameterSpec.Builder(alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256).build())
        return generator.generateKey()
    }

    fun isEnabled(): Boolean = prefs.getBoolean("enabled", false)
    fun hasPin(): Boolean = prefs.contains("cipher")

    fun verify(pin: String): Boolean {
        val encoded = prefs.getString("cipher", null) ?: return false
        return try {
            val iv = Base64.decode(prefs.getString("iv", ""), Base64.NO_WRAP)
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, key(), GCMParameterSpec(128, iv))
            val actual = String(cipher.doFinal(Base64.decode(encoded, Base64.NO_WRAP)), Charsets.UTF_8)
            actual == pin
        } catch (_: Exception) { false }
    }

    fun setPin(pin: String) {
        require(pin.length == 6 && pin.all { it.isDigit() }) { "PIN باید دقیقاً ۶ رقم باشد" }
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, key())
        val encrypted = cipher.doFinal(pin.toByteArray(Charsets.UTF_8))
        prefs.edit().putString("cipher", Base64.encodeToString(encrypted, Base64.NO_WRAP))
            .putString("iv", Base64.encodeToString(cipher.iv, Base64.NO_WRAP)).apply()
    }

    fun setEnabled(enabled: Boolean) { prefs.edit().putBoolean("enabled", enabled).apply() }
    fun removePin() { prefs.edit().remove("cipher").remove("iv").putBoolean("enabled", false).apply() }
}
