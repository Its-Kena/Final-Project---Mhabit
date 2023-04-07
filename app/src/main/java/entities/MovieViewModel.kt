package entities

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository : MovieRepository) : ViewModel() {

    val allMovies : LiveData<MutableList<Movie>> = movieRepository.allMovies.asLiveData()

    fun insert(movie: Movie) = viewModelScope.launch {
        movieRepository.insert(movie)
    }

    fun delete(movie: Movie) = viewModelScope.launch {
        movieRepository.delete(movie)
    }

    class MovieViewModelFactory(private val movieRepository: MovieRepository)
        :ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(movieRepository) as T
            }
            throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
        }

        }

}