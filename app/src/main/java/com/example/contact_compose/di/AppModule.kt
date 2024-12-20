package com.example.contact_compose.di

import android.app.Application
import com.example.contact_compose.model.ContactDao
import com.example.contact_compose.model.database.AppDatabase
import com.example.contact_compose.model.repository.ContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase{
        return AppDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun provideContactDao(database: AppDatabase): ContactDao{
        return database.contactDao()
    }

    @Provides
    @Singleton
    fun provideContactRepository(contactDao: ContactDao): ContactRepository{
        return ContactRepository(contactDao)
    }
}