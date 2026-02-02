package com.example.planify.main.features.auth.domain.services

import com.example.planify.main.features.auth.entities.User

interface AuthService {
    fun isAuthorized(): Boolean
}