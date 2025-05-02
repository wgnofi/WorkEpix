package com.example.standardprotocols.data

data class Issue(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val date: String? = null,
    val managerId: String? = null,
    val status: String? = null
)

// status: Fixed, Raised