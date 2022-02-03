package com.example.monitoringapp.di

import com.example.monitoringapp.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.Response

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val builder = Retrofit.Builder()
            .baseUrl(Constants.API_URL_DEV)
            .addConverterFactory(GsonConverterFactory.create(gson))

        val httpClient = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            //.addInterceptor(TokenHeader())
            //.addInterceptor(interceptor())

        return builder.client(httpClient.build()).build()
    }

    /*private fun interceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }*/

   /* @Provides
    fun provideAuthenticationApiClient(retrofit: Retrofit): AuthenticationApiClient {
        return retrofit.create(AuthenticationApiClient::class.java)
    }*/

}
