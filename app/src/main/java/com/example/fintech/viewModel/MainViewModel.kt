package com.example.fintech.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintech.model.*
import com.example.fintech.network.RetrofitService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var cookies: String? = null

    var api = RetrofitService().authentication
    var _apiCaller = MutableLiveData<AuthenticationDO>()
    val apiCaller: LiveData<AuthenticationDO>
        get() = _apiCaller

    var otpApi = RetrofitService().otpAuthentication
    var verifyOtpApi = RetrofitService().otpVerification
    var logoutApi = RetrofitService().logout

    fun authenticate(idToken: IdToken) {
        viewModelScope.launch {
            try {
                val result = api.authentication(idToken)
                _apiCaller.postValue(result.body())
                cookies = result.headers()["Set-Cookie"].toString().substringAfter("=").substringBefore("; ")
                Log.e("GoogleSigninCookie", cookies!!)
            } catch (e: Exception) {
                Log.e("mainViewModel", "Error with authentication")
                Log.e("mainViewModel", e.toString())
            }
        }
    }

    fun otpAuthentication(phone: Phone){
        viewModelScope.launch {
            try {
                val result = otpApi.otpAuthentication(phone)
               _apiCaller.postValue(result.body())
                Log.e("mainViewModel", "otpAuthentication Successful")
            } catch (e: Exception) {
                Log.e("mainViewModel", "Error with otpAuthentication")
                Log.e("mainViewModel", e.toString())
            }
        }
    }

    fun otpVerification(verifyOtpDO: VerifyOtpDO){
        viewModelScope.launch {
            try {
                val result = verifyOtpApi.otpVerification(verifyOtpDO)
                _apiCaller.postValue(result.body())
                cookies = result.headers()["Set-Cookie"].toString().substringAfter("=").substringBefore("; ")
                val bodyContent = result.body()
                Log.e("OtpSignInCookie", cookies!!)
                Log.e("VerifyOtpBody", bodyContent.toString())
                Log.e("mainViewModel", "otpVerification Successful")
            } catch (e: Exception) {
                Log.e("mainViewModel", "Error with otpAuthentication")
                Log.e("mainViewModel", e.toString())
            }
        }
    }

    fun logout(cookies: String){
        viewModelScope.launch {
            try {
                val result = logoutApi.logout(cookies)
                _apiCaller.postValue(result.body())
                Log.e("mainViewModel", "logout Successful")
            } catch (e: Exception) {
                Log.e("mainViewModel", "Error with logout")
                Log.e("mainViewModel", e.toString())
            }
        }
    }
}