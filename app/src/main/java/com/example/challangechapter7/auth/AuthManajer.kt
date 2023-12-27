package com.example.challangechapter7.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


class AuthManajer(private val dataStore: DataStore<Preferences>) {
    companion object{
        val KEY_EMAIL = stringPreferencesKey("KEY_EMAIL")
        val KEY_USERNAME = stringPreferencesKey("KEY_USERNAME")
        val KEY_PASSWORD = stringPreferencesKey("KEY_PASSWORD")
        val KEY_NAMA_LENGKAP = stringPreferencesKey("KEY_NAMA_LENGKAP")
        val KEY_TANGGAL_LAHIR = stringPreferencesKey("KEY_TANGGAL_LAHIR")
        val KEY_ALAMAT = stringPreferencesKey("KEY_ALAMAT")
        val KEY_FAVORITE_POST = stringPreferencesKey("KEY_FAVORITE_POST")


        @Volatile
        private var instance: AuthManajer? = null
        fun getInstance(dataStore: DataStore<Preferences>): AuthManajer {
            return instance ?: synchronized(this){
                instance ?: AuthManajer(dataStore)
            }.also { instance = it }
        }
    }

    data class UserCredentials(val username:String,val email:String,val password:String,val nama: String,val tanggal: String, val alamat: String,val favoritePost: String)

     fun getUserCredential(): Flow<UserCredentials> {
        return dataStore.data.map { preferences ->
            UserCredentials(
                preferences[KEY_EMAIL].orEmpty(),
                preferences[KEY_USERNAME].orEmpty(),
                preferences[KEY_PASSWORD].orEmpty(),
                preferences[KEY_NAMA_LENGKAP].orEmpty(),
                preferences[KEY_TANGGAL_LAHIR].orEmpty(),
                preferences[KEY_ALAMAT].orEmpty(),
                preferences[KEY_FAVORITE_POST].orEmpty()

            )
        }
    }

    suspend fun setCredentials(email: String,username: String,password: String){
        dataStore.edit {preference ->
            preference[KEY_EMAIL] = email
            preference[KEY_USERNAME] = username
            preference[KEY_PASSWORD] = password

        }
    }

    suspend fun updateIdentitas(nama:String,tanggal:String,alamat:String){
        dataStore.edit {preference ->
            preference[KEY_NAMA_LENGKAP]= nama
            preference[KEY_TANGGAL_LAHIR]= tanggal
            preference[KEY_ALAMAT]= alamat
        }
    }


    suspend fun loginStatus(): Boolean {
        val email = dataStore.data.firstOrNull()?.get(KEY_EMAIL).orEmpty()
        val password = dataStore.data.firstOrNull()?.get(KEY_PASSWORD).orEmpty()
        return email.isNotEmpty() && password.isNotEmpty()
    }



    suspend fun login(email: String, password: String): Boolean {
        val userCredentials = getUserCredential().firstOrNull()

        // Periksa apakah userCredentials tidak null sebelum membandingkan
        return userCredentials?.email == email && userCredentials.password == password
    }


    suspend fun getFavoritePost(): String {
        return dataStore.data.firstOrNull()?.get(KEY_FAVORITE_POST).orEmpty()
    }


    suspend fun setFavoritePost(favoritePost: Int) {
        dataStore.edit { preference ->
            preference[KEY_FAVORITE_POST] = favoritePost.toString()
        }
    }




    suspend fun logout(){
        dataStore.edit {preferences ->
            preferences[KEY_EMAIL] = ""
            preferences[KEY_USERNAME] = ""
            preferences[KEY_PASSWORD] = ""
        }
    }

}

