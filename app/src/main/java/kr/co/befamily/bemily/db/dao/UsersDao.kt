package kr.co.befamily.bemily.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.befamily.bemily.db.entity.UsersEntity

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(users: List<UsersEntity>)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<UsersEntity>>
}