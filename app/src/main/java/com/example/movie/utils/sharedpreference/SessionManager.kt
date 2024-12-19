package com.example.movie.utils.sharedpreference

import android.content.Context
import android.content.SharedPreferences

class SessionManager(private val context: Context) {
    companion object {
        private const val SHARED_PREFERENCE_NAME = "SessionPref"
        private const val KEY_SESSION_ID = "session_id"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveSessionId(sessionId: String){
        editor.putString(KEY_SESSION_ID, sessionId)
        editor.apply()
    }

    fun getSessionId(): String?{
        return sharedPreferences.getString(KEY_SESSION_ID, null);
    }

    fun clearSessionId(){
        editor.remove(KEY_SESSION_ID)
        editor.apply()
    }
}