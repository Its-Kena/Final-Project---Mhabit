package entities

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("select * from Movies")
    fun getAll(): Flow<MutableList<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie?)

    @Delete
    suspend fun delete(movie: Movie)
}