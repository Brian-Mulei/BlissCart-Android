package com.mukshi.blisscart.di

import android.content.Context
import com.mukshi.blisscart.App
import com.mukshi.blisscart.remote.api.ApiService
 import com.mukshi.blisscart.repository.AuthRepository
import com.mukshi.blisscart.utils.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val sharedPreferences = App.instance.getSharedPreferences("your_app_prefs", Context.MODE_PRIVATE )
        val accessToken = sharedPreferences.getString("token", "")

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
        //   .addInterceptor(loggingInterceptor)
            .build()

        //10.0.2.2
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Replace with your API base URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideAuthRepository(
//        ApiService: ApiService
//    ): AuthRepository {
//        return AuthRepository(loginApiService)
//    }
}