package com.example.challangechapter7.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.challangechapter7.R
import com.example.challangechapter7.auth.AuthManajer
import com.example.challangechapter7.auth.AuthRepository
import com.example.challangechapter7.auth.AuthViewModel
import com.example.challangechapter7.auth.datastore
import com.example.challangechapter7.databinding.FragmentDetailBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.network.TMDBApi
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.network.TMDBRetrofitBuilder
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.repository.TMDBRepository
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.viewmodel.TMDBViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding


    private val TmdbApi: TMDBApi by lazy {
        TMDBRetrofitBuilder.instanceTMDB
    }

    private val repository: TMDBRepository by lazy {
        TMDBRepository(TmdbApi)
    }

    private val viewModel: TMDBViewModel by viewModels {
        TMDBViewModel.Factory(repository)
    }

    private val authManager: AuthManajer by lazy {
        AuthManajer.getInstance(requireContext().datastore)
    }

    private val authrepository: AuthRepository by lazy {
        AuthRepository(authManager)
    }

    private val authviewModel: AuthViewModel by viewModels {
        AuthViewModel.Factory(authrepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movieId") ?: 0


        viewModel.detailDataMovie(movieId).observe(viewLifecycleOwner, Observer { movieData ->
            // Menampilkan data di UI
            val image = movieData?.body()?.posterPath // Ubah ke "posterPath"
            val title = movieData?.body()?.title
            val date = movieData?.body()?.releaseDate
            val overview = movieData?.body()?.overview
            val popular = movieData?.body()?.popularity

            val baseUrlImg = TMDBRetrofitBuilder.BASE_URL_IMG
            val posterPath = image
            val imgUrl = baseUrlImg + posterPath

            binding.tvTitle.text = "Movie Title: $title"
            binding.tvDate.text = "Release Date: $date"
            binding.tvOverview.text = "Overview: $overview"
            binding.tvAuthor.text = "Popularity: $popular"
            Glide.with(binding.ivMovie).load(imgUrl).into(binding.ivMovie)
        })
    }
}