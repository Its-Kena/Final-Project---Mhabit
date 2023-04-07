package entities

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy {MovieDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {MovieRepository(database.movieDao())}

    }
