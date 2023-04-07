package entities

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    val allMovies : Flow<MutableList<Movie>> = movieDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movie : Movie) {
        movieDao.insert(movie)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(movie : Movie) {
        movieDao.update(movie)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(movie : Movie) {
        movieDao.delete(movie)
    }

}