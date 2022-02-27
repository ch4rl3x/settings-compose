# settings-compose
## Current Compose Version: 1.1.0

<a href="https://github.com/Ch4rl3x/settings-compose/actions?query=workflow%3ABuild"><img src="https://github.com/ch4rl3x/settings-compose/actions/workflows/build.yml/badge.svg" alt="Build"></a>
<a href="https://www.codefactor.io/repository/github/ch4rl3x/settings-compose"><img src="https://www.codefactor.io/repository/github/ch4rl3x/settings-compose/badge" alt="CodeFactor" /></a>
<a href="https://repo1.maven.org/maven2/de/charlex/compose/settings-datastore/"><img src="https://img.shields.io/maven-central/v/de.charlex.compose/settings-datastore" alt="Maven Central" /></a>

# Add to your project

Add actual settings-compose library:

```groovy
dependencies {
    implementation 'de.charlex.compose:settings-datastore:1.0.0-rc01'
}
```

# How does it work?

```kotlin

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ...
    setContent {
        //Provide SettingsDataStore around
        CompositionLocalProvider(LocalSettingsDataStore provides settingsDataStore) {
            ...
        }
    }
}

```

```kotlin
Setting(
    key = booleanPreference("key1", false),
    saveDebounceMillis = 250 //Optional
) { value, onValueChanged ->
    Switch(
        checked = value,
        onCheckedChange = onValueChanged
    )
}
```

That's it!

License
--------

    Copyright 2022 Alexander Karkossa

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
