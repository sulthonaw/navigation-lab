package uth.praktikum.praktikumpapb.data.preferences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

internal val Context.userPreferencesDataStore by preferencesDataStore(
    name = "user_preferences"
)