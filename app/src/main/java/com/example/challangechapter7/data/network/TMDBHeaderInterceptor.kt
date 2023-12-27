package rizkyfadilah.binar.synrgy6.android.learning.challengechapter5.data.network

import okhttp3.Interceptor
import okhttp3.Response


class TMDBHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Authorization", "Bearer ${TMDBRetrofitBuilder.ACCESS_TOKEN}")
            .addHeader("accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}
