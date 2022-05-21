package com.listfilm.andika.view.fragment


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
import com.binar.challengechapterenam.datastore.UserManager
import com.listfilm.andika.R
import com.listfilm.andika.view.adapter.AdapterHome
import com.listfilm.andika.viewmodel.ViewModelMovie
import kotlinx.android.synthetic.main.fragment_home.view.*



class HomeFragment : Fragment() {

    lateinit var adapternewfilm : AdapterHome
    lateinit var adapterrecommend : AdapterHome
    lateinit var adaptertopselling :AdapterHome
    lateinit var userManager : UserManager
    lateinit var email : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        var homelinear = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        //New Movie~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        adapternewfilm = AdapterHome(){
            val bund = Bundle()
            bund.putParcelable("detailfilm", it)
            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bund)

        }
        view.list.adapter = adapternewfilm
        view.list.layoutManager = homelinear
        getNewFilm()

//        //Recommended Movie~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//        adapterrecommend  = AdapterHome(){
//            val bund = Bundle()
//            bund.putParcelable("detailfilm", it)
//            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bund)
//
//        }
//        view.listrecommend.adapter = adapterrecommend
//        view.listrecommend.layoutManager = homelinear
//
//        //Top Selling Movie~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        adaptertopselling = AdapterHome(){
//            val bund = Bundle()
//            bund.putParcelable("detailfilm", it)
//            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bund)
//
//        }
//        view.listrecommend.adapter = adaptertopselling
//        view.listrecommend.layoutManager = homelinear


        userManager = UserManager(requireContext())
        userManager.userUsername.asLiveData().observe(requireActivity()){
            view.welcome.text = it.toString()
        }


        view.profile.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        view.homelove.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }

        return view
    }


//    fun getPopularFilm(){
//
//        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelMovie::class.java)
//        viewModel.getLiveFilmObserver().observe(requireActivity()) {
//            if(it != null){
//                adapterfilm.setDataFilm(it)
//                adapterfilm.notifyDataSetChanged()
//                adapternew.setDataFilm(it)
//                adapternew.notifyDataSetChanged()
//            }
//        }
//        viewModel.makeFilmApi()
//    }


    fun getNewFilm(){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelMovie::class.java)
        viewModel.getLiveNewFilmObserver().observe(requireActivity()) {
            if(it != null){
                adapternewfilm.setDataFilm(it)
                adapternewfilm.notifyDataSetChanged()
            }
        }
        viewModel.NewMovieApi()
    }
}