package com.example.standardprotocols.data

data class Leave(
    val id: String? = null,
    val userId: String? = null,
    val date: String? = null,
    val reason: String? = null,
    val appliedDate: String? = null,
    val managerId: String? = null,
    val status: String? = null
)

// status: Rejected, Accepted, Pending