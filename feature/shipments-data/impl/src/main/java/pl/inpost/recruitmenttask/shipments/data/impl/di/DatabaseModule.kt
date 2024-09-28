package pl.inpost.recruitmenttask.shipments.data.impl.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.shipments.data.impl.database.AppDatabase

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun database(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app-database")
            .build()
}
