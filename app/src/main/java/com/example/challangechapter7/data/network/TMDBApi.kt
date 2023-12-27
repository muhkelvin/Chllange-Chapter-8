package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.challangechapter7.data.model.MovieResponse


interface TMDBApi {
    @GET("discover/movie")
    suspend fun getMovies(): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int
    ): Response<MovieResponse.Results>

    /*@GET("discover/movie")
    suspend fun getMoviesHeaderInterceptor(
        // adding header di interface
        @HeaderMap header: Map<String, String> =
            mapOf(
                "Authorization" to "Bearer ${TMDBRetrofitBuilder.ACCESS_TOKEN}",
                "accept" to "application/json"
            ),
    ): Response<MovieResponse>*/
}