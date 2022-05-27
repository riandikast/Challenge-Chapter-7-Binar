package com.listfilm.andika.view.fragment


import UserManager
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.listfilm.andika.R
import com.listfilm.andika.view.adapter.AdapterHome
import com.listfilm.andika.viewmodel.ViewModelMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.stream.Collectors.toList


class HomeFragment : Fragment() {

    lateinit var adapternewfilm : AdapterHome
    lateinit var adapterrecommend : AdapterHome
    lateinit var adapterpopular :AdapterHome
    lateinit var userManager : UserManager
    lateinit var email : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        var homelinear = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        var poplinear = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        var reclinear = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        //New Movie~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        adapternewfilm = AdapterHome(){
            val bund = Bundle()
            bund.putParcelable("detailfilm", it)
            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bund)

        }

        view.list.adapter = adapternewfilm
        view.list.layoutManager = homelinear
        getNewFilm()

        //Popular Movie~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        adapterpopular = AdapterHome(){
            val bund = Bundle()
            bund.putParcelable("detailfilm", it)
            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bund)

        }
        view.listpopular.adapter = adapterpopular
        view.listpopular.layoutManager = poplinear
        getPopularFilm()

        //Recommended Movie~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        adapterrecommend  = AdapterHome(){
            val bund = Bundle()
            bund.putParcelable("detailfilm", it)
            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bund)

        }
        view.listrecommend.layoutManager = reclinear
        view.listrecommend.adapter = adapterrecommend

        getRecFilm()



        userManager = UserManager(requireContext())
        userManager.userUsername.asLiveData().observe(requireActivity()){
            view.welcome.text = it.toString()
        }

        view.profile.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }


        return view
    }



    fun getPopularFilm(){

        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelMovie::class.java)
        viewModel.popularMovie.observe(requireActivity()) {
            if(it != null){

                adapterpopular .setDataFilm(it)
                adapterpopular .notifyDataSetChanged()

            }
        }

    }


    fun getNewFilm(){

        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelMovie::class.java)
        viewModel.newFilm.observe(requireActivity()) {
            if(it != null){
                adapternewfilm.setDataFilm(it)
                adapternewfilm.notifyDataSetChanged()
            }
        }

    }

    fun getRecFilm(){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelMovie::class.java)
        viewModel.recommendedMovie.observe(requireActivity()) {
            if(it != null){
                adapterrecommend.setDataFilm(it)
                adapterrecommend.notifyDataSetChanged()
            }
        }

    }


}