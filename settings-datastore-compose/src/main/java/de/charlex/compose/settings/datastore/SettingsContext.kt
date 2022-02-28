package de.charlex.compose.settings.datastore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import de.charlex.compose.cache.rememberLocalCache
import de.charlex.settings.datastore.IDataStorePreference
import de.charlex.settings.datastore.SettingsDataStore
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@Composable
fun <T> rememberSettingsValue(
    key: IDataStorePreference<T>,
    saveDebounceMillis: Long? = null,
    settingsDataStore: SettingsDataStore = LocalSettingsDataStore.current,
): Pair<T, (T) -> Unit> {
    val coroutineScope = rememberCoroutineScope()
    val settingsValue by settingsDataStore.get(key).collectAsState(initial = key.defaultValue)
    return rememberLocalCache(value = settingsValue, saveDebounceMillis = saveDebounceMillis) {
        coroutineScope.launch {
            settingsDataStore.put(key, it)
        }
    }
}

@FlowPreview
@Composable
fun <T> SettingsContext(
    key: IDataStorePreference<T>,
    saveDebounceMillis: Long? = null,
    settingsDataStore: SettingsDataStore = LocalSettingsDataStore.current,
    content: @Composable (value: T, onValueChange: (T) -> Unit) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val settingsValue by settingsDataStore.get(key).collectAsState(initial = key.defaultValue)
    val (value, onValueChange) = rememberLocalCache(value = settingsValue, saveDebounceMillis = saveDebounceMillis) {
        coroutineScope.launch {
            settingsDataStore.put(key, it)
        }
    }
    content(value, onValueChange)
}
