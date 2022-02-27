package de.charlex.compose.settings.datastore

import androidx.compose.runtime.staticCompositionLocalOf
import de.charlex.settings.datastore.SettingsDataStore

val LocalSettingsDataStore = staticCompositionLocalOf<SettingsDataStore> {
    error("SettingsDataStore not present")
}