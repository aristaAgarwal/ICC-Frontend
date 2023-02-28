package com.example.fintech.network

class RetrofitService {
    var authentication = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var otpAuthentication = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var otpVerification = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var logout = RetrofitBuilder.getInstance().create(Authentication::class.java)
    var products = RetrofitBuilder.getInstance().create(GetAllProducts::class.java)
    var addProduct = RetrofitBuilder.getInstance().create(PostAddProduct::class.java)
}