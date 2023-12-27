package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TMDBRetrofitBuilder {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_IMG = "https://image.tmdb.org/t/p/w500"
    const val ACCESS_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzY2E3ZGI2ZmQyODU1MzljN2I3OWFiZDFkODA4YjZlOSIsInN1YiI6IjY1NmY0NDVjODgwNTUxMDEwMDBmY2QyMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.QCHbsvspX8QciA8qHLwq7T6K1JtveqANu_IFqz5N4U4"


    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val headerInterceptor get() = TMDBHeaderInterceptor()

    private val client = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(logging)
        .build()

    val instanceTMDB: TMDBApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(TMDBApi::class.java)
    }

}
