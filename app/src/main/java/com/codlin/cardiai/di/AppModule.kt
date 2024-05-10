package com.codlin.cardiai.di

import android.content.Context
import com.codlin.cardiai.BuildConfig
import com.codlin.cardiai.data.datasource.local.datastore.UserPreferences
import com.codlin.cardiai.data.datasource.remote.interceptor.AuthInterceptor
import com.codlin.cardiai.data.datasource.remote.service.AuthService
import com.codlin.cardiai.data.datasource.remote.service.PatientService
import com.codlin.cardiai.data.datasource.remote.service.RecordService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().serializeNulls().create()
    }

    @Provides
    @Singleton
    fun provideOkHttp(authInterceptor: AuthInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            client.addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cardiai-112e49359ba9.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun providePatientService(retrofit: Retrofit): PatientService {
        return retrofit.create(PatientService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecordService(retrofit: Retrofit): RecordService {
        return retrofit.create(RecordService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
}