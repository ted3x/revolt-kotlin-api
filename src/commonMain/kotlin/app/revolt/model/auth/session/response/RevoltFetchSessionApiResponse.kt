package app.revolt.model.auth.session.response

import app.revolt.utils.RevoltApiConstants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RevoltFetchSessionApiResponse(
    @SerialName(RevoltApiConstants.ID)
    val id: String,
    val name: String
)