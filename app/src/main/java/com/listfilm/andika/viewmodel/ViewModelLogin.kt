package com.listfilm.andika.viewmodel

import UserManager
import android.content.Context

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class ViewModelLogin() :ViewModel() {
    lateinit var userManager: UserManager


    fun loginState(context: Context): LiveData<String> {
        userManager = UserManager(context)
        return userManager.userLogin.asLiveData()
    }

}