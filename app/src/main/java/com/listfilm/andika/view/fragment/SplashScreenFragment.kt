package com.listfilm.andika.view.fragment


import UserManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import com.listfilm.andika.R
import com.listfilm.andika.viewmodel.ViewModelLogin
import com.listfilm.andika.viewmodel.ViewModelUser
import kotlin.properties.Delegates


class SplashScreenFragment : Fragment() {
    lateinit var userManager : UserManager

    private lateinit var viewModelLogin : ViewModelLogin

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        userManager = UserManager(requireActivity())
        viewModelLogin = ViewModelProvider(this).get(ViewModelLogin::class.java)
            Handler(Looper.getMainLooper()).postDelayed({

                viewModelLogin.loginState(requireContext()).observe(viewLifecycleOwner) {
                    if (it == "false"){
                        view.findNavController().navigate(
                            R.id.action_splashScreenFragment_to_loginFragment, null,
                            NavOptions.Builder()
                                .setPopUpTo(
                                    R.id.splashScreenFragment,
                                    true
                                ).build())

                    }else if (it == "true"){
                        view.findNavController().navigate(
                            R.id.action_splashScreenFragment_to_homeFragment, null,
                            NavOptions.Builder()
                                .setPopUpTo(
                                    R.id.splashScreenFragment,
                                    true
                                ).build())
                    }
                }
            }, 2000)

        return view
    }

}