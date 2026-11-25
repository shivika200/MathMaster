
package com.example.mathmaster.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        QuestionEntity::class,
        ProgressEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class MathMasterDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    abstract fun progressDao(): ProgressDao
}
