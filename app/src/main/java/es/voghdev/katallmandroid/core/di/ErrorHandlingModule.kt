package es.voghdev.katallmandroid.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ErrorHandlingModule {

    @Provides
    fun provideErrorHandler(): @JvmSuppressWildcards (Throwable) -> Unit = { e ->
        e.printStackTrace()
    }
}
