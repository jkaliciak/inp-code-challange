package pl.inpost.recruitmenttask.common.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.data.model.AppResult

@Suppress("TooGenericExceptionCaught")
abstract class SuspendUseCase<in PARAM, RESULT : Any>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: PARAM): AppResult<RESULT> {
        return try {
            withContext(coroutineDispatcher) {
                val result = execute(parameters)
                AppResult.Success(result)
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    protected abstract suspend fun execute(parameters: PARAM): RESULT
}