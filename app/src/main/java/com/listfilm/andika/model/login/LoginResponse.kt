package com.listfilm.andika.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)
