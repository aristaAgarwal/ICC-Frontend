package com.example.fintech.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintech.Model.AuthenticationDO
import com.example.fintech.Model.IdToken
import com.example.fintech.network.RetrofitService
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    var api = RetrofitService().authentication
    var _apiCaller = MutableLiveData<AuthenticationDO>()
    val apiCaller: LiveData<AuthenticationDO>
          get() =  _apiCaller


    fun authenticate(idToken: IdToken){
        viewModelScope.launch {
            try {
                val result = api.authentication(idToken)
                _apiCaller.postValue(result.body())
            }
            catch (e:Exception){
                Log.e("mainViewModel","Error with authentication")
            }
        }
    }
}