package com.listfilm.andika.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listfilm.andika.model.movie.GetMovie
import com.listfilm.andika.network.ApiClient
import retrofit2.Call
import retrofit2.Response


class ViewModelMovie (): ViewModel() {
    var liveDataFilm : MutableLiveData<List<GetMovie>> = MutableLiveData()
    var liveDataNewFilm : MutableLiveData<List<GetMovie>> = MutableLiveData()

    fun getLiveFilmObserver() : MutableLiveData<List<GetMovie>> {
        return liveDataFilm
    }

    fun getLiveNewFilmObserver() : MutableLiveData<List<GetMovie>> {
        return liveDataNewFilm
    }



    fun PopularMovieApi(){
        ApiClient.instance.getPopularMovie()
            .enqueue(object : retrofit2.Callback<List<GetMovie>>{
                override fun onResponse(
                    call: Call<List<GetMovie>>,
                    response: Response<List<GetMovie>>
                ) {
                    if (response.isSuccessful){
                        liveDataFilm.postValue(response.body())

                    }else{
                        liveDataFilm.postValue(null)

                    }
                }

                override fun onFailure(call: Call<List<GetMovie>>, t: Throwable) {
                    liveDataFilm.postValue(null)
                }
            })
            }

    fun NewMovieApi(){
        ApiClient.instance.getNewMovie()
            .enqueue(object : retrofit2.Callback<List<GetMovie>>{
                override fun onResponse(
                    call: Call<List<GetMovie>>,
                    response: Response<List<GetMovie>>
                ) {
                    if (response.isSuccessful){
                        liveDataNewFilm.postValue(response.body())

                    }else{
                        liveDataNewFilm.postValue(null)

                    }
                }

                override fun onFailure(call: Call<List<GetMovie>>, t: Throwable) {
                    liveDataNewFilm.postValue(null)
                }
            })
    }
}

