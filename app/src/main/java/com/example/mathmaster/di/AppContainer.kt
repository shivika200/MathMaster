
package com.example.mathmaster.di

import android.content.Context
import androidx.room.Room
import com.example.mathmaster.data.local.MathMasterDatabase
import com.example.mathmaster.data.remote.RetrofitClient
import com.example.mathmaster.data.repository.MathRepository

class AppContainer(context: Context) {

    val database: MathMasterDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            MathMasterDatabase::class.java,
            "mathmaster_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    val mathRepository: MathRepository =
        MathRepository(
            database = database,
            apiService = RetrofitClient.apiService
        )
}
