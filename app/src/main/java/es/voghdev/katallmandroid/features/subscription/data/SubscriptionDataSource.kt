package es.voghdev.katallmandroid.features.subscription.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionDataSource @Inject constructor() {
    fun getUserSubscription(): Flow<UserSubscription> = flow {
        delay(2500)
        emit(UserSubscription.FREE)
    }
}
