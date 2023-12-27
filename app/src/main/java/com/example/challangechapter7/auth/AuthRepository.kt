package com.example.challangechapter7.auth

import com.example.challangechapter7.auth.AuthManajer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(private val authdataStore: AuthManajer) {

    fun getCredentials():Flow<AuthManajer.UserCredentials>{
        return authdataStore.getUserCredential()
    }

    suspend fun setUserCredentials(email: String, username:String,password:String){
        authdataStore.setCredentials(email,username,password)
    }

//    suspend fun updateCredentials(email: String, username: String, password: String) {
//        authdataStore.updateCredentials(email, username, password)
//    }

    fun getFavoritePost(): Flow<String> {
        return authdataStore.getUserCredential().map { userCredentials ->
            userCredentials.favoritePost.toString()
        }
    }

    suspend fun updateIdentitas(nama:String,tanggal:String,alamat:String){
        authdataStore.updateIdentitas(nama,tanggal,alamat)
    }

    suspend fun setFavoritePost(favoritePost: Int) {
        authdataStore.setFavoritePost(favoritePost)
    }
    suspend fun login(email: String, password: String): Boolean {
        return authdataStore.login(email, password)
    }

    suspend fun logout(){
        authdataStore.logout()
    }
}