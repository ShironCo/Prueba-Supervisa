package com.example.pruebatecnicasupervisa.di

import android.app.Application
import android.os.Vibrator
import androidx.room.Room
import com.example.pruebatecnicasupervisa.data.local.dao.TaskDao
import com.example.pruebatecnicasupervisa.data.local.database.AppDatabase
import com.example.pruebatecnicasupervisa.data.local.repository.TaskRepositoryImpl
import com.example.pruebatecnicasupervisa.data.remote.PokemonApi
import com.example.pruebatecnicasupervisa.data.remote.reposiroty.PokemonRepositoryImpl
import com.example.pruebatecnicasupervisa.domain.repository.PokemonRepository
import com.example.pruebatecnicasupervisa.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
/**
 Aquí se inyectan automáticamente las clases necesarias gracias a Dagger Hilt,
 evitando así la creación manual de instancias en cada uso.
 Esto mejora la modularidad, el mantenimiento y la escalabilidad del código.
 */
@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideTaskDao(application: Application): TaskDao{
        val db = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "task_db"
            ).build()
        return db.dao
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao
    ): TaskRepository{
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideVibrator(context: Application): Vibrator{
        return context.getSystemService(Vibrator::class.java) as Vibrator
    }


    @Provides
    @Singleton
    fun provideApi(): PokemonApi{
        return Retrofit.Builder()
            .baseUrl(PokemonApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(OkHttpClient.Builder().build())
            .build().create(PokemonApi::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: PokemonApi
    ): PokemonRepository{
        return PokemonRepositoryImpl(
            api = api
        )
    }

}