package app.vineshbuilds.githubpullviewer.rest

import app.vineshbuilds.githubpullviewer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestClient private constructor() {
    private object HOLDER {
        private val OKHTTP_CLIENT: OkHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .build()
        val APIs: Apis = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHTTP_CLIENT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Apis::class.java)
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
        val apis: Apis by lazy { HOLDER.APIs }
    }
}