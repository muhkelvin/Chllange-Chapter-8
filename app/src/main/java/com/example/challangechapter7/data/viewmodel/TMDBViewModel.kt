package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import retrofit2.Response
import com.example.challangechapter7.data.model.MovieResponse
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.repository.TMDBRepository

class TMDBViewModel(private val repository: TMDBRepository) : ViewModel() {

    val data: LiveData<Response<MovieResponse>> get() = repository.getMovie().asLiveData()
//    val dataDetail: MutableLiveData<Response<MovieResponse.Results>> = MutableLiveData()

    fun detailDataMovie(movieId: Int): LiveData<Response<MovieResponse.Results>> =
        repository.getMovieDetail(movieId).asLiveData()


    class Factory(private val repository: TMDBRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TMDBViewModel::class.java))
                return TMDBViewModel(repository) as T
            throw RuntimeException("No Viewmodel")
        }
    }
}