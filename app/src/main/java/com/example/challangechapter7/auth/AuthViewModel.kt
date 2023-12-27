package com.example.challangechapter7.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository):ViewModel() {

    val userCredential: LiveData<AuthManajer.UserCredentials> = repository.getCredentials().asLiveData()

    fun setUserCredentials(email: String?, username: String?, password: String?) {
        viewModelScope.launch {
            // Menangani nilai null sebelum memanggil metode setUserCredential
            val safeEmail = email ?: ""
            val safeUsername = username ?: ""
            val safePassword = password ?: ""

            repository.setUserCredentials(safeEmail, safeUsername, safePassword)
        }
    }

//    fun setUserCredentials(email:String,username:String,password:String){
//        viewModelScope.launch {
//            repository.setUserCredential(email, username, password)
//        }
//    }

    fun updateIdentitas(nama:String,tanggal:String,alamat:String){
        viewModelScope.launch {
            repository.updateIdentitas(nama,tanggal,alamat)
        }
    }


    fun setFavoritePost(favoritePost: Int) {
        viewModelScope.launch {
            repository.setFavoritePost(favoritePost)
        }
    }

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        val success = repository.login(email, password)
        emit(success)
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getFavoritePost(): LiveData<String> {
        return repository.getFavoritePost().asLiveData()
    }

    class Factory(private val repository: AuthRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
                return AuthViewModel(repository) as T
            }
            throw RuntimeException("no ViewModel Attach")
        }
    }
}