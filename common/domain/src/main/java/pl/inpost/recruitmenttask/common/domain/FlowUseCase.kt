package pl.inpost.recruitmenttask.common.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import pl.inpost.recruitmenttask.data.model.AppResult

abstract class FlowUseCase<in PARAM, RESULT : Any>(private val coroutineDispatcher: CoroutineDispatcher) {

    open operator fun invoke(parameters: PARAM? = null): Flow<AppResult<RESULT>> {
        return execute(parameters)
            .catch { exception -> emit(AppResult.Error(exception)) }
            .flowOn(coroutineDispatcher)
    }

    protected abstract fun execute(parameters: PARAM? = null): Flow<AppResult<RESULT>>
}