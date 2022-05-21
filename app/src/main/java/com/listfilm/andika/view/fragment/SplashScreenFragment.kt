package com.listfilm.andika.view.fragment


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.binar.challengechapterenam.datastore.UserManager
import com.listfilm.andika.R


class SplashScreenFragment : Fragment() {
    lateinit var userManager : UserManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        userManager = UserManager(requireContext())
        Handler().postDelayed({
            userManager.userLogin.asLiveData().observe(requireActivity()){
                if (it.equals("false")){
                    view.findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment,null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.splashScreenFragment,
                                true).build())

                }else if (it.equals("true")){
                    view.findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment,null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.splashScreenFragment,
                                true).build())
                }
            }
        }, 2000)
     return view
    }
}