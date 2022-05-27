package com.listfilm.andika.view.fragment

import UserManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragmentXKt
import com.listfilm.andika.R
import com.listfilm.andika.model.movie.GetMoviee
import com.listfilm.andika.room.FavoriteDB
import com.listfilm.andika.room.FavoriteFilm
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlin.properties.Delegates



class DetailFragment : Fragment() {

    var db: FavoriteDB? = null
    var film : List<FavoriteFilm>? = null

    lateinit var id : String
    lateinit var title : String
    lateinit var director : String
    lateinit var createdAt : String
    lateinit var releaseDate : String
    lateinit var synopsis : String
    lateinit var image: String
    lateinit var email : String
    lateinit var trailer : String
    lateinit var type : String
    lateinit var userManager : UserManager

    lateinit var toggleFavorite : String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        userManager = UserManager(requireContext())
        val getfilm = arguments?.getParcelable<GetMoviee>("detailfilm")
        val getfilmfromfav = arguments?.getParcelable<FavoriteFilm>("detailfilmfromfav")
        db = FavoriteDB.getInstance(requireActivity())

        if (getfilm != null){
            view.text1.text = getfilm.title
            view.text2.text = getfilm.director
            view.text3.text = getfilm.releaseDate
            view.text4.text = getfilm.synopsis
            Glide.with(requireContext()).load(getfilm.image).into(view.gambar1)
            id = getfilm.id.toString()
            title = getfilm.title.toString()
            director = getfilm.director.toString()
            createdAt = getfilm.createdAt.toString()
            synopsis = getfilm.synopsis.toString()
            image = getfilm.image.toString()
            releaseDate = getfilm.releaseDate
            trailer = getfilm.demo
            type = getfilm.type

        }

        if (getfilmfromfav!=null){
            view.text1.text = getfilmfromfav.title
            view.text2.text = getfilmfromfav.director
            view.text3.text = getfilmfromfav.releaseDate
            view.text4.text = getfilmfromfav.synopsis
            Glide.with(requireContext()).load(getfilmfromfav.image).into(view.gambar1)

            id = getfilmfromfav.id.toString()
            title = getfilmfromfav.title
            director = getfilmfromfav.director
            createdAt = getfilmfromfav.createdAt
            synopsis = getfilmfromfav.synopsis
            image = getfilmfromfav.image
            releaseDate = getfilmfromfav.releaseDate
            trailer = getfilmfromfav.demo
            type = getfilmfromfav.favtype

        }

        email = ""
        toggleFavorite = "false"


        userManager.userEmail.asLiveData().observe(requireActivity()){
            email = it
            GlobalScope.async {
                film = db?.getFavoriteDao()?.checkFav(email,  title)
                val checkDB = film?.size !=0
                    if (checkDB){
                        view.btnfavorite.setImageResource(R.drawable.love)
                        toggleFavorite = "true"

                                                                                                                                              //codebyandika
                    }else {
                        view.btnfavorite.setImageResource(R.drawable.unlove )
                        toggleFavorite = "false"

                    }
            }
            }


        view.btnfavorite.setOnClickListener {
            toggleButton()
        }

        val fragment = childFragmentManager
        val youtubefragment  =  fragment.findFragmentById(R.id.youtube_fragment) as? YouTubePlayerSupportFragmentXKt
        val change = fragment.findFragmentById(R.id.youtube_fragment)
        val newConfig = resources.configuration
        var c = change?.view?.layoutParams

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            c!!.height = 800
            c!!.width = 1400


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
        youtubefragment?.initialize("AIzaSyBad_BbLDvWwL6QuOILNyo4eMBmPmq4jyg", object: YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                wasRestored: Boolean
            ) {
                if (player == null) return
                if (wasRestored)
                    player.play()
                else{
                    player.cueVideo("$trailer")
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                }
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {

            }

        })

        return view
    }

    fun toggleButton(){
        for (data in toggleFavorite){
            if (toggleFavorite == "true"  ){
                btnfavorite.setImageResource(R.drawable.unlove)
                toggleFavorite = "false"
                GlobalScope.async {
                    db?.getFavoriteDao()?.deleteFilmID(email,  title)
                }
                break
            }

            if (toggleFavorite == "false"  ) {
                btnfavorite.setImageResource(R.drawable.love)
                toggleFavorite= "true"
                GlobalScope.async {
                    db?.getFavoriteDao()?.addFilm(
                        FavoriteFilm(
                            null,
                            email,
                            createdAt,
                            director,
                            id.toInt(),
                            image,
                            releaseDate,
                            synopsis,
                            title,
                            trailer,
                            type


                                                                                                            //codebyandika
                        )
                    )
                }
                break
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val fragment = childFragmentManager

        val change = fragment.findFragmentById(R.id.youtube_fragment)
        var c = change?.view?.layoutParams

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            c!!.height = 100
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            c!!.height = 100
        }
    }


}