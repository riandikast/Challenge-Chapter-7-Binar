package com.listfilm.andika.view.fragment

import UserManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager


import com.listfilm.andika.R
import com.listfilm.andika.room.FavoriteDB
import com.listfilm.andika.room.FavoriteFilm
import com.listfilm.andika.view.adapter.AdapterFavorite
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class FavoriteFragment : Fragment() {
    var db: FavoriteDB? = null
    var film : FavoriteFilm? = null
    lateinit var userManager : UserManager
    lateinit var adapterFavorite : AdapterFavorite
    lateinit var emailUser : String
    var idUser by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favorite, container, false)
        db = FavoriteDB.getInstance(requireContext())

        emailUser =""
        userManager = UserManager(requireContext())
        userManager.userUsername.asLiveData().observe(requireActivity()){
            view.welcomefav.text = it.toString()
        }

//        view.profilefav.setOnClickListener {
//            view.findNavController().navigate(R.id.action_favoriteFragment_to_profileFragment)
//        }
        userManager.userID.asLiveData().observe(requireActivity()){
            idUser = it.toInt()
        }
        userManager.userEmail.asLiveData().observe(requireActivity()){
            emailUser = it
            GlobalScope.launch {
//            film = db?.getFavoriteDao()?.getFilmID(id)!!
                db?.getFavoriteDao()?.getAllFav()
                Log.d("www", emailUser)
                val listdata = db?.getFavoriteDao()?.getFav(it)
                requireActivity().runOnUiThread {
                    listdata.let {
                        if (listdata?.size == 0) {
                            view.checkdatafav.visibility = View.VISIBLE
                            view.checkdataimage.visibility = View.VISIBLE

                        }else{
                            view.checkdatafav.visibility = View.GONE
                            view.checkdataimage.visibility = View.GONE
                        }

                        adapterFavorite = AdapterFavorite(){
                            val bund = Bundle()
                            bund.putParcelable("detailfilmfromfav", it)
                            view.findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment,bund)
                        }
                        view.listfav.adapter = adapterFavorite
                        adapterFavorite.setDataFav(it!!)
                    }
                }
            }

        }
        view.listfav.layoutManager = GridLayoutManager(requireContext(),3)


        return view
    }


}