package kr.co.befamily.bemily.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.co.befamily.bemily.db.dao.UsersDao
import kr.co.befamily.bemily.db.entity.UsersEntity

@Database(entities = [UsersEntity::class], version = 1)
abstract class UsersDatabase : RoomDatabase()  {

    abstract val usersDao: UsersDao

    companion object {
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase {
            synchronized(this) {
                var instance: UsersDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java,
                        "users"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}