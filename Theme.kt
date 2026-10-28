package ir.privatesmsvault.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colors = lightColorScheme(
    primary = Color(0xFF8068B5), onPrimary = Color.White,
    secondary = Color(0xFFB9A8DE), background = Color(0xFFFAF7FC),
    surface = Color.White, onSurface = Color(0xFF29252F),
    surfaceVariant = Color(0xFFF0EAF7), onSurfaceVariant = Color(0xFF716B78)
)
@Composable fun VaultTheme(content: @Composable () -> Unit) { MaterialTheme(colorScheme = colors, content = content) }
