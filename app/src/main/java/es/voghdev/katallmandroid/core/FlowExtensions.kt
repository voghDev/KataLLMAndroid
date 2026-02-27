package es.voghdev.katallmandroid.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.catchAndHandle(
    onError: (Throwable) -> Unit,
    action: suspend (Throwable) -> Unit,
): Flow<T> = catch { e ->
    onError(e)
    action(e)
}
