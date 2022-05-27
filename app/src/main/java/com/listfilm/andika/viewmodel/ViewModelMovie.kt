package com.listfilm.andika.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.listfilm.andika.model.movie.GetMoviee

import com.listfilm.andika.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ViewModelMovie  @Inject constructor(private val api : ApiService): ViewModel() {

    private var liveDataNewFilm = MutableLiveData<List<GetMoviee>>()
    val newFilm : LiveData<List<GetMoviee>> = liveDataNewFilm
    private var liveDataPopuarFilm = MutableLiveData<List<GetMoviee>>()
    val popularMovie : LiveData<List<GetMoviee>> = liveDataPopuarFilm
    private var liveDataRecFilm = MutableLiveData<List<GetMoviee>>()
    val recMovie : LiveData<List<GetMoviee>> = liveDataRecFilm


    init {
        viewModelScope.launch {
            val datanewmovie = api.getNewMovie()
            delay(2000)
            liveDataNewFilm.value = datanewmovie

            val datarecmovie = api.getRecMovie()
            delay(2000)
            liveDataRecFilm.value = datarecmovie

            val datapopularmovie = api.getPopularMovie()
            delay(2000)
            liveDataPopuarFilm.value = datapopularmovie

    }


//    init {
//        viewModelScope.launch {
//            val datapopularmovie = api.getPopularMovie()
//            delay(2000)
//            liveDataNewFilm.value = datapopularmovie
//        }
//    }



//    init {
//        viewModelScope.launch {
//            val datarecommendmovie = api.getRecMovie()
//            delay(2000)
//            liveDataNewFilm.value = datarecommendmovie
//        }
//    }


}}

