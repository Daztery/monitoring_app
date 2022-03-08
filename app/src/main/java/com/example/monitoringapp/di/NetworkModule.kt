package com.example.monitoringapp.di

import com.example.monitoringapp.data.network.api.*
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.PreferencesHelper
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
import okhttp3.logging.HttpLoggingInterceptor

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
            .addInterceptor(TokenHeader())
            .addInterceptor(interceptor())

        return builder.client(httpClient.build()).build()
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideAuthenticationApiClient(retrofit: Retrofit): AuthenticationApiClient {
        return retrofit.create(AuthenticationApiClient::class.java)
    }

    @Provides
    fun provideUserApiClient(retrofit: Retrofit): UserApiClient {
        return retrofit.create(UserApiClient::class.java)
    }

    @Provides
    fun provideEmergencyTypeApiClient(retrofit: Retrofit): EmergencyTypeApiClient {
        return retrofit.create(EmergencyTypeApiClient::class.java)
    }

    @Provides
    fun providePriorityTypeApiClient(retrofit: Retrofit): PriorityTypeApiClient {
        return retrofit.create(PriorityTypeApiClient::class.java)
    }

    @Provides
    fun provideReportTypeApiClient(retrofit: Retrofit): ReportApiClient {
        return retrofit.create(ReportApiClient::class.java)
    }

    @Provides
    fun provideMedicalCenterApiClient(retrofit: Retrofit): MedicalCenterApiClient {
        return retrofit.create(MedicalCenterApiClient::class.java)
    }

    @Provides
    fun providePrescriptionApiClient(retrofit: Retrofit): PrescriptionApiClient {
        return retrofit.create(PrescriptionApiClient::class.java)
    }

}

class TokenHeader : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = PreferencesHelper.token
        val original = chain.request()
        var newRequest = original

        if (!token.isNullOrBlank()) {

            val builder = original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .method(original.method, original.body)

            newRequest = builder.build()

        }


        return chain.proceed(newRequest)
    }
}


