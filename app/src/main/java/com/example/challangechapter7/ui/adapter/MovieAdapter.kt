package com.example.challangechapter7.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challangechapter7.R
import com.example.challangechapter7.data.model.MovieResponse
import com.example.challangechapter7.databinding.ItemMovieBinding
import rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.network.TMDBRetrofitBuilder


class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.TmdbViewHolder>() {

    private val dataList: MutableList<MovieResponse.Results> = mutableListOf()

    fun addAll(tmdbList: List<MovieResponse.Results>) {
        dataList.clear()
        dataList.addAll(tmdbList)
        notifyDataSetChanged()
    }

    inner class TmdbViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieResponse.Results) {

            val baseUrlImg = TMDBRetrofitBuilder.BASE_URL_IMG
            val posterPath = data.posterPath
            val imgUrl = baseUrlImg + posterPath

//            binding..text = data.title
            Glide.with(binding.ivMovie)
                .load(imgUrl)
                .placeholder(R.drawable.splash)
                .into(binding.ivMovie)

            // Jika item di klik, kirimkan data movie ke listener
            itemView.setOnClickListener {
                // Mengambil host activity untuk navigasi
                val navController = Navigation.findNavController(itemView)

                // Membuat bundle untuk mengirim data ke DetailFragment
                val bundle = bundleOf("movieId" to data.id)

                // Melakukan navigasi ke DetailFragment
                navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)

                // Menampilkan Toast bahwa data berhasil dikirim
                Toast.makeText(itemView.context, "Data berhasil dikirim: ${data.title}", Toast.LENGTH_SHORT).show()
            }




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TmdbViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TmdbViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TmdbViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
