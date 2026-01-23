package com.example.planify.main.features.auth.domain.repositories

interface AuthRepository {
    fun hasTokens(): Boolean
}