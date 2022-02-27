package de.charlex.compose.settings.datastore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import de.charlex.settings.datastore.IDataStorePreference
import de.charlex.settings.datastore.SettingsDataStore
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@FlowPreview
@Composable
fun <T> Setting(
    key: IDataStorePreference<T>,
    saveDebounceMillis: Long? = null,
    settingsDataStore: SettingsDataStore = LocalSettingsDataStore.current,
    content: @Composable (value: T, onValueChanged: (T) -> Unit) -> Unit
) {
    // remember a channel we can send to.
    // Conflated because we want old values to be replaced by new.
    // queryChannel.offer will always succeed.
    val settingsDataStoreChannel = remember { Channel<T>(Channel.CONFLATED) }

    saveDebounceMillis?.let {

        // LaunchedEffect will run our valueChanged and cancel everything when
        // this composable leaves the composition. If we get a different
        // key object or settingsDataStore object this will cancel the old effect and launch a new one.
        LaunchedEffect(key, settingsDataStore) {
            // Use withContext here to change dispatchers if desired
            settingsDataStoreChannel.receiveAsFlow()
                .debounce(it)
                .collect {
                    settingsDataStore.put(key, it)
                }
        }
    }

    val value by settingsDataStore.get(key = key).collectAsState(initial = key.defaultValue)

    var internalValue by remember(value) { mutableStateOf(value, structuralEqualityPolicy()) }
    val coroutineScope = rememberCoroutineScope()

    content(
        value = internalValue,
        onValueChanged = {
            coroutineScope.launch {
                internalValue = it
                if (saveDebounceMillis != null) {
                    settingsDataStoreChannel.trySend(it)
                } else {
                    settingsDataStore.put(key, it)
                }
            }
        }
    )
}
