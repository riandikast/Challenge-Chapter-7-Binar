package com.listfilm.andika.view.fragment

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
                        customToast()
                    }

                } else {
                    toast = "Konfirmasi Password Tidak Sesuai"
                    customToast()
                }
            } else {
                toast = "Harap isi semua data"
                customToast()
            }
        }
       return view
    }


    fun regisUser(username: String, email: String, password: String) {
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveRegisObserver().observe(requireActivity(), Observer {
            if (it  == null){
                Toast.makeText(requireContext(), "Gagal Register", Toast.LENGTH_LONG ).show()
            }else{
                Toast.makeText(requireContext(), "Berhasil Register", Toast.LENGTH_LONG ).show()
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

    fun customToast() {
        val text = toast
        val toast = Toast.makeText(
            requireActivity()?.getApplicationContext(),
            text,
            Toast.LENGTH_LONG
        )
        val text1 =
            toast.getView()?.findViewById(android.R.id.message) as TextView
        val toastView: View? = toast.getView()
        toastView?.setBackgroundColor(Color.TRANSPARENT)
        text1.setTextColor(Color.RED);
        text1.setTextSize(15F)
        toast.show()
        toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 1420)
    }
}