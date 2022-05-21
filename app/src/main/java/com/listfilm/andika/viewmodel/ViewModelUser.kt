package com.listfilm.andika.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listfilm.andika.model.login.LoginResponse
import com.listfilm.andika.model.update.UpdateResponse
import com.listfilm.andika.model.user.GetAllUserItem
import com.listfilm.andika.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUser : ViewModel(){

    var liveDataNewUserItem : MutableLiveData<List<GetAllUserItem>> = MutableLiveData()
    var liveDataLogin : MutableLiveData<LoginResponse> = MutableLiveData()
    var liveDataRegis : MutableLiveData<GetAllUserItem> = MutableLiveData()
    var liveDataUpdate : MutableLiveData<GetAllUserItem> = MutableLiveData()

    fun getLiveUserObserver() : MutableLiveData<List<GetAllUserItem>> {
        return liveDataNewUserItem
    }

    fun getLiveLoginObserver() : MutableLiveData<List<GetAllUserItem>> {
        return liveDataNewUserItem
    }

    fun getLiveRegisObserver() : MutableLiveData<GetAllUserItem> {
        return liveDataRegis
    }

    fun getLiveUpdateObserver() : MutableLiveData<GetAllUserItem> {
        return liveDataUpdate
    }

    fun updateDataUser(id : Int, dataUser : UpdateResponse){
        ApiClient.instance.updateNewUser(dataUser, id.toString() )
            .enqueue(object : retrofit2.Callback<GetAllUserItem> {
                override fun onResponse(
                    call: Call<GetAllUserItem>,
                    response: Response<GetAllUserItem>
                ) {
                    liveDataUpdate.postValue(response.body())

                }
                override fun onFailure(call: Call<GetAllUserItem>, t: Throwable) {
                    liveDataUpdate.postValue(null)
                }
            })
    }

    fun userApi(){
        ApiClient.instance.getAllNewUser()
            .enqueue(object : retrofit2.Callback<List<GetAllUserItem>>{
                override fun onResponse(
                    call: Call<List<GetAllUserItem>>,
                    getAllItem: Response<List<GetAllUserItem>>
                ) {
                    if (getAllItem.isSuccessful){
                        liveDataNewUserItem.postValue(getAllItem.body())

                    }else{
                        liveDataNewUserItem.postValue(null)

                    }
                }
                override fun onFailure(call: Call<List<GetAllUserItem>>, t: Throwable) {
                    liveDataNewUserItem.postValue(null)
                }
            })
    }

    fun login(email :String, password : String){
        ApiClient.instance.loginNewUser(email, password).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>)
            {
                if (response.isSuccessful){
                    liveDataLogin.postValue(response.body())

                }else{
                    liveDataLogin.postValue(null)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                liveDataLogin.postValue(null)

            }
        })
    }

    fun regisUser(username: String, email: String, password: String) {
        ApiClient.instance.registerNew(username, email, password, "","","","")
            .enqueue(object : Callback<GetAllUserItem> {
                override fun onResponse(
                    call: Call<GetAllUserItem>,
                    response: Response<GetAllUserItem>
                ) {
                    if (response.isSuccessful) {
                        liveDataRegis.postValue(response.body())
                    } else {
                        liveDataRegis.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetAllUserItem>, t: Throwable) {
                    liveDataRegis.postValue(null)
                }
            })
    }

}