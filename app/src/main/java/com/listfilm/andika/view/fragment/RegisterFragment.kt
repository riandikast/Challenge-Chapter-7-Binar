package com.listfilm.andika.view.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.listfilm.andika.R
import com.listfilm.andika.model.user.GetAllUserItem

import com.listfilm.andika.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*


class RegisterFragment : Fragment() {
    lateinit var regisemailtext: String

    lateinit var dataUser: List<GetAllUserItem>
    lateinit var viewModel: ViewModelUser
    lateinit var password: String
    lateinit var toast: String
    lateinit var register: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        getDataUserItem()
        register = "true"

        view.btnregis.setOnClickListener {
            val username = regisusername.text.toString()
            regisemailtext = regisemail.text.toString()
            password = regispassword.text.toString()
            val confirmpass = confirmpassword.text.toString()

            if (regisusername.text.isNotEmpty() && regisemail.text.isNotEmpty()
                && regispassword.text.isNotEmpty()
                && confirmpassword.text.isNotEmpty()
            ) {
                if (password == confirmpass) {
                    for (i in dataUser.indices) {
                        if (regisemailtext == dataUser[i].email) {
                            register = "false"
                            break
                        } else {
                            register = "true"
                        }
                    }

                    if (register == "true") {
                        regisUser(username, regisemailtext, password)
                        view.findNavController()
                            .navigate(R.id.action_registerFragment_to_loginFragment)


                    } else {
                        toast = "Email Sudah Terdaftar"
                        customFailureToast(requireContext(), toast)
                    }

                } else {
                    toast = "Konfirmasi Password Tidak Sesuai"
                    customFailureToast(requireContext(), toast)
                }
            } else {
                toast = "Harap isi semua data"
                customFailureToast(requireContext(), toast)
            }
        }
       return view
    }


    fun regisUser(username: String, email: String, password: String) {
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveRegisObserver().observe(requireActivity(), Observer {
            if (it  != null){
                toast = "Registrasi Berhasil"
                customSuccessToast(requireContext(), toast)
            }else{
                toast = "Registrasi Gagal"
                customFailureToast(requireContext(), toast)
            }
        })
        viewModel.regisUser(username,email,password)
    }

    fun getDataUserItem() {
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveUserObserver().observe(viewLifecycleOwner, Observer {
            dataUser = it
        })
        viewModel.userApi()
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