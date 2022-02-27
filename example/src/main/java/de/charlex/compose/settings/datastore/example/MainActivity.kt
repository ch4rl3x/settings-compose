package de.charlex.compose.settings.datastore.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.charlex.compose.settings.datastore.LocalSettingsDataStore
import de.charlex.compose.settings.datastore.Setting
import de.charlex.compose.settings.datastore.example.theme.SettingsTheme
import de.charlex.settings.datastore.SettingsDataStore
import de.charlex.settings.datastore.booleanPreference
import de.charlex.settings.datastore.stringPreference

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingsDataStore = SettingsDataStore.create(this)
        setContent {
            Body(settingsDataStore = settingsDataStore)
        }
    }
}

@Composable
fun Body(settingsDataStore: SettingsDataStore) {
    SettingsTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            CompositionLocalProvider(LocalSettingsDataStore provides settingsDataStore) {
                Column {
                    Setting(key = stringPreference("email", "max.mustermann@googlemail.com")) { value, onValueChanged ->
                        TextField(value = value, onValueChange = onValueChanged)
                    }

                    Setting(key = booleanPreference("key1", false)) { value, onValueChanged ->
                        Switch(checked = value, onCheckedChange = onValueChanged)
                    }

                    Setting(key = booleanPreference("key2", false)) { value, onValueChanged ->
                        Checkbox(checked = value, onCheckedChange = onValueChanged)
                    }

                    Setting(key = stringPreference("email_two", "maria.mustermann@googlemail.com"), saveDebounceMillis = 5000) { value, onValueChanged ->
                        TextField(value = value, onValueChange = onValueChanged)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SettingsTheme {
        Body(settingsDataStore = SettingsDataStore.createInMemory())
    }
}
