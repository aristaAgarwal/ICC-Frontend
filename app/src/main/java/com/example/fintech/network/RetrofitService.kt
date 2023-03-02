package com.example.fintech.network

import retrofit2.create

class RetrofitService {
    var authentication = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var otpAuthentication = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var otpVerification = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var userInfo = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var logout = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var products = RetrofitBuilder.getInstance().create(GetAllProducts::class.java)
    var addProduct = RetrofitBuilder.getInstance().create(CartProduct::class.java)
    var getAllProduct = RetrofitBuilder.getInstance().create(CartProduct::class.java)
    var removeProduct = RetrofitBuilder.getInstance().create(CartProduct::class.java)
    var checkout = RetrofitBuilder.getInstance().create(Checkout::class.java)
    var checkReferral = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var videos = RetrofitBuilder.getInstance().create(GetTutorials::class.java)
}