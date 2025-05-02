package com.example.standardprotocols.data

data class User(
    var uid: String = "",
    val displayName: String? = null,
    val email: String? = null,
    val role: String? = null,
    val managerId: String? = null
)

