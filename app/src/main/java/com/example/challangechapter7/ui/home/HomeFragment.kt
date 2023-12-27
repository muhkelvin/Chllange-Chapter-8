package com.example.challangechapter7.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.challangechapter7.R
import com.example.challangechapter7.auth.AuthManajer
import com.example.challangechapter7.auth.AuthRepository
import com.example.challangechapter7.auth.AuthViewModel
import com.example.challangechapter7.auth.datastore
import com.example.challangechapter7.databinding.FragmentHomeBinding
import com.example.challangechapter7.ui.adapter.MovieAdapter
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.network.TMDBApi
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.network.TMDBRetrofitBuilder
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.repository.TMDBRepository
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.viewmodel.TMDBViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val adapter: MovieAdapter by lazy {
        MovieAdapter()
    }

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authviewModel.userCredential.observe(viewLifecycleOwner) { userCredential ->
            if (userCredential != null) {
                binding.tvUsername.text = userCredential.username
            }
        }


        // Mengatur RecyclerView
        binding.rvMovie.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                val tmdbList = response.body()?.results
                if (tmdbList != null) {
                    // Memperbarui adapter dengan data baru
                    adapter.addAll(tmdbList)
                    Log.e("TmdbFragment", "Berhasil")
                    for (tmdb in tmdbList) {
                        Log.e("MovieFragment", "Movie: ${tmdb.title}")
                    }
                } else {
                    Log.e("TmdbFragment", "Gagal mendapatkan data Tmdb")
                }
            }
        }
    }

}
