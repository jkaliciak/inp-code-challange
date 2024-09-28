package pl.inpost.recruitmenttask.data.model

sealed class AppResult<out T : Any> {

    data class Loading<out T : Any>(val isLoading: Boolean) : AppResult<T>()
    data class Success<out T : Any>(val data: T) : AppResult<T>()
    data class Error(val throwable: Throwable) : AppResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading[isLoading=$isLoading]"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
        }
    }
}

//fun <T : Any> AppResult<T>.onError(block: (AppResult.Error) -> Unit) {
//    if (this is AppResult.Error) block(this)
//}