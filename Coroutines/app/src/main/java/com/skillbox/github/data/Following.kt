package com.skillbox.github.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Following(
    val login: String,
    val id: Long,
    val avatar_url: String,
    val url: String,
    val repos_url: String,
    val type: String
)
