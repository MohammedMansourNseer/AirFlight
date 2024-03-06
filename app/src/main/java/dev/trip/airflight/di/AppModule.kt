package dev.trip.airflight.di

import dev.trip.airflight.data.flights.datasoruce.FlightRemoteDatasource
import dev.trip.airflight.data.flights.datasoruce.remote.FlightRemoteDatasourceImpl
import dev.trip.airflight.data.flights.datasoruce.remote.network.ApiService
import dev.trip.airflight.data.flights.repository.FlightsRepositoryImpl
import dev.trip.airflight.domain.flights.repository.FlightsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
        .baseUrl("https://pharmacademy.co/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideFlightsRemoteDatasource(apiService: ApiService): FlightRemoteDatasource = FlightRemoteDatasourceImpl(apiService)

    @Provides
    @Singleton
    fun provideFlightsRepository(
        competitionRemote: FlightRemoteDatasource,
    ): FlightsRepository =
        FlightsRepositoryImpl(competitionRemote)


}

