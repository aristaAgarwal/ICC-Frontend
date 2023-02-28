package com.example.fintech.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintech.constants.AppPreferences
import com.example.fintech.model.*
import com.example.fintech.network.RetrofitService
import kotlinx.coroutines.launch
import okhttp3.Cookie

class MainViewModel : ViewModel() {
    var cookies: String? = null

    var api = RetrofitService().authentication
    private var _apiCaller = MutableLiveData<BaseResponseDO>()
    val apiCaller: LiveData<BaseResponseDO>
        get() = _apiCaller

    private var _productApiCaller = MutableLiveData<ProductsDO>()
    val productApiCaller: LiveData<ProductsDO>
        get() = _productApiCaller

    var otpApi = RetrofitService().otpAuthentication
    var verifyOtpApi = RetrofitService().otpVerification
    var logoutApi = RetrofitService().logout
    var allProducts = RetrofitService().products
    var addProduct = RetrofitService().addProduct

    fun authenticate(idToken: IdToken) {
        viewModelScope.launch {
            try {
                val result = api.authentication(idToken)
                _apiCaller.postValue(result.body())
                val coo = result.headers()
                Log.e("header", coo.toString())
                cookies = result.headers()["Set-Cookie"]
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
                Log.e("mainViewModel", "otp sent Successfully")
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
                val coo = result.headers()
                Log.e("header", coo.toString())
                cookies = result.headers()["Set-Cookie"].toString()
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

    fun getProducts(){
        viewModelScope.launch {
            try {
                val result = allProducts.getAllProducts()
                _productApiCaller.postValue(result.body())
                Log.e("mainViewModel", "fetched all products successfully")
            } catch (e:Exception){
                Log.e("mainViewModel", "error with fetching all products")
                Log.e("getProducts",e.toString())
            }
        }
    }

    fun addProducts(product: AddProductToCart, cookie: String){
        viewModelScope.launch {
            try {
                val result = addProduct.postAddProduct(product, cookie)
                _productApiCaller.postValue(result.body())
                Log.e("mainViewModel", "product added successfully")
            } catch (e:Exception){
                Log.e("mainViewModel", "error with adding product")
                Log.e("getProducts",e.toString())
            }
        }
    }
}