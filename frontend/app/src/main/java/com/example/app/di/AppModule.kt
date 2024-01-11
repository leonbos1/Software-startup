package com.example.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.app.data.remote.BackendApi
import com.example.app.data.remote.BackendApi.Companion.BASE_URL
import com.example.app.data.repository.ProductRepository
import com.example.app.data.repository.ProductRepositoryImplementation
import com.example.app.domain.use_cases.AddProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesAddProductUseCase(repository: ProductRepository): AddProductUseCase {
        return AddProductUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesApiService(): BackendApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackendApi::class.java)
    }

    @Provides
    @Singleton
    fun providesProductRepository(
        apiService: BackendApi
    ): ProductRepository {
        return ProductRepositoryImplementation(
            backendApi = apiService
        )
    }

}