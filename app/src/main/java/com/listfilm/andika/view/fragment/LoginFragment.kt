package com.listfilm.andika.view.fragment

import UserManager
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.listfilm.andika.R
import com.listfilm.andika.model.user.GetAllUserItem
import com.listfilm.andika.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class LoginFragment : Fragment() {

    lateinit var dataUser : List<GetAllUserItem>
    lateinit var viewModel : ViewModelUser
    lateinit var email: String
    lateinit var password: String
    lateinit var toast : String
    var salah by Delegates.notNull<Boolean>()
    lateinit var userManager : UserManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        userManager = UserManager(requireContext())


        val daftar = view.findViewById<TextView>(R.id.daftar2)
        val login = view.findViewById<Button>(R.id.btnlogin)
        getDataUserItem()
        daftar.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        login.setOnClickListener {
            if (loginemail.text.isNotEmpty() && loginpassword.text.isNotEmpty()){
                email = loginemail.text.toString()
                password = loginpassword.text.toString()

                check()

            }
            else{
                toast = "Harap Isi Semua Data"
                customFailureToast(requireContext(), toast)
            }
        }
        return view
    }


    fun getDataUserItem(){
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveUserObserver().observe(viewLifecycleOwner, Observer {
            dataUser = it

        })
        viewModel.userApi()
    }

    fun check() {
        userManager = UserManager(requireContext())

        for (i in dataUser.indices) {
            if (email == dataUser[i].email && password == dataUser[i].password) {
                GlobalScope.launch {

                    userManager.saveDataLogin("true")
                    userManager.saveDataUser(dataUser[i].id, dataUser[i].email, dataUser[i].password, dataUser[i].username, dataUser[i].completeName,dataUser[i].dateofbirth, dataUser[i].address, dataUser[i].image )
                }


                toast = "Login Berhasil"
                customSuccessToast(requireContext(), toast)

                view?.findNavController()
                    ?.navigate(R.id.action_loginFragment_to_homeFragment, null,
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.loginFragment,
                                true
                            ).build())
                salah = false
                break

            }else{
                salah = true


            }

        }
        if (salah){
            toast = "Email atau Password Salah"
            customFailureToast(requireContext(), toast)
        }
    }




    fun customFailureToast(context: Context?, msg: String?) {
        val inflater = LayoutInflater.from(context)
        val layout: View = inflater.inflate(R.layout.error_toast, null)
        val text = layout.findViewById<View>(R.id.errortext) as? TextView
        text?.text = msg
        text?.setPadding(20, 0, 20, 0)
        text?.textSize = 22f
        text?.setTextColor(Color.WHITE)
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        layout.setBackgroundColor(Color.DKGRAY)
        toast.setView(layout)
        toast.show()
    }

    fun customSuccessToast(context: Context?, msg: String?) {
        val inflater = LayoutInflater.from(context)
        val layout: View = inflater.inflate(R.layout.success_toast, null)
        val text = layout.findViewById<View>(R.id.successtext) as? TextView
        text?.text = msg
        text?.setPadding(20, 0, 20, 0)
        text?.textSize = 22f
        text?.setTextColor(Color.WHITE)
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        layout.setBackgroundColor(Color.DKGRAY)
        toast.setView(layout)
        toast.show()
    }






}