package es.voghdev.katallmandroid.features.profile.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileDataSource @Inject constructor() {
    fun getUser(): Flow<User> = flow {
        emit(
            User(
                name = "Jane Doe",
                address = "742 Evergreen Terrace, Springfield",
                phoneNumber = "+1 (555) 123-4567",
                profilePictureUrl = "",
            )
        )
    }
}
