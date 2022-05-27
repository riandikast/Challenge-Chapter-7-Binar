package com.listfilm.andika.view.fragment

import UserManager
import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController

import com.bumptech.glide.Glide
import com.listfilm.andika.R
import com.listfilm.andika.model.update.UpdateResponse
import com.listfilm.andika.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.logout_dialog.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class ProfileFragment : Fragment() {

    lateinit var viewModel: ViewModelUser
    lateinit var username: String
    lateinit var email : String
    lateinit var password : String
    lateinit var idUser : String
    lateinit var userManager : UserManager
    lateinit var image : String
    lateinit var cn : String
    lateinit var dateofbirth : String
    lateinit var address : String
    lateinit var dataUser : UpdateResponse
    lateinit var toast : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        userManager = UserManager(requireContext())
        userManager.userImage.asLiveData().observe(requireActivity()){
            if (it !="" && it != null){
                image = it
                Log.d("www" , image)
                Glide.with(requireActivity()).load( it ).into(view.pp2)
            }else{
                image = ""
                Log.d("www" , image)
            }
        }
        userManager.userUsername.asLiveData().observe(requireActivity()){
            view.update1.setText(it)
        }
        userManager.userNamaLengkap.asLiveData().observe(requireActivity()){
            view.update2.setText(it)
        }
        userManager.userBirth.asLiveData().observe(requireActivity()){
            view.update3.setText(it)
        }
        userManager.userAddress.asLiveData().observe(requireActivity()){
            view.update4.setText(it)
        }
        userManager.userEmail.asLiveData().observe(requireActivity()){
            email = it
        }
        userManager.userPass.asLiveData().observe(requireActivity()){
            password = it
        }



        view.btnupdate.setOnClickListener {
            userManager.userID.asLiveData().observe(requireActivity()){
                idUser = it
            }
            username = view.update1.text.toString()
            cn = view.update2.text.toString()
            dateofbirth = view.update3.text.toString()
            address = view.update4.text.toString()

            GlobalScope.launch {
                userManager.saveDataUser(idUser , email, password, username, cn, dateofbirth, address, image )
            }
            dataUser = UpdateResponse(idUser.toInt(), address, cn,dateofbirth,email, password, username, image  )
            updateDataUser(idUser.toInt(), dataUser)

            view.findNavController().navigate(R.id.action_profileFragment_to_homeFragment)

        }

        view.update3.setOnClickListener{
           datePicker()
        }

        view.btnlogout.setOnClickListener {
            val custom = LayoutInflater.from(requireContext()).inflate(R.layout.logout_dialog, null)
            val a = AlertDialog.Builder(requireContext())
                .setView(custom)
                .create()

            custom.btnlogouttidak.setOnClickListener {
                a.dismiss()
            }

            custom.btnlogoutya.setOnClickListener {

                GlobalScope.launch {
                    userManager.deleteDataLogin()
                    userManager.deleteDataUser()

                }
                a.dismiss()
                view.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
            a.show()
        }

        view.addpp.setOnClickListener {
            if (askForPermissions()) {
                startGallery()
            }
        }
        return view
    }

    fun updateDataUser(id : Int, dataUser : UpdateResponse){
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveUpdateObserver().observe(requireActivity(), Observer {
            if (it  != null){
                toast = " Berhasil Update Data"
                customSuccessToast(requireContext(), toast)
            }else{
                toast = "Gagal Update Data"
                customFailureToast(requireContext(), toast)
            }
        })
        viewModel.updateDataUser(id, dataUser)
    }

    private fun startGallery() {
        val cameraIntent = Intent(Intent.ACTION_GET_CONTENT)
        cameraIntent.type = "image/*"
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, 2000)
        }
    }

    fun isPermissionsAllowed(): Boolean {
        return if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }

    fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(requireActivity(),arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2000)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        when (requestCode) {
            2000 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                } else {
                    // permission is denied, you can ask for permission again, if you want
                    //  askForPermissions()
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", "com.binar.challengechapterenam", null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2000 && data != null){
            pp.setImageURI(data?.data)
            image = data?.data.toString()
        }else {

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

    fun datePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var m = ""

        val dpd = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            when (monthOfYear){
                0 ->{
                    m  = "January"
                }
                1 ->{
                    m  = "February"
                }
                2 ->{
                    m  = "March"
                }
                3 ->{
                    m  = "April"
                }
                4 ->{
                    m  = "May"
                }
                5 ->{
                    m  = "June"
                }
                6 ->{
                    m  = "July"
                }
                7 ->{
                    m  = "August"
                }
                8 ->{
                    m  = "September"
                }
                9 ->{
                    m  = "October"
                }
                10 ->{
                    m  = "November"
                }
                11 ->{
                    m  = "December"
                }

            }


            update3.setText("" + dayOfMonth + " " + m + ", " + year)

        }, year, month, day)

        dpd.show()
    }

}