package app.vineshbuilds.githubpullviewer.model

import com.google.gson.annotations.SerializedName

data class PR(
        val url: String,
        val id: Long,
        val number: Int,
        val title: String,
        @SerializedName("created_at") val createdAt: String
)