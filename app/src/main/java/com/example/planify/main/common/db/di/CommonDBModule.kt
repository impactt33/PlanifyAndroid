package com.example.planify.main.common.db.di

import android.content.Context
import androidx.room.Room
import com.example.planify.main.common.db.AppDatabase
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

object CommonDBModule {
    @Singleton
    @Provides
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app_db"
        ).fallbackToDestructiveMigration(false).build()
    }
}
