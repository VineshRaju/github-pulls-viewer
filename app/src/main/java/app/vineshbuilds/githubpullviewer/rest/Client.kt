package app.vineshbuilds.githubpullviewer.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client private constructor() {
    private object HOLDER {
        val RETROFIT_CLIENT: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
        val client: Retrofit by lazy { HOLDER.RETROFIT_CLIENT }
    }
}