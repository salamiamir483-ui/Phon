package ir.privatesmsvault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import ir.privatesmsvault.ui.VaultApp
import ir.privatesmsvault.ui.theme.VaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { VaultTheme { CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) { VaultApp() } } }
    }
}
