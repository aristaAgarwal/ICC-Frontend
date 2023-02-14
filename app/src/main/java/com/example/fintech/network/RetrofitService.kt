package com.example.fintech.network

class RetrofitService {
    var authentication = RetrofitBuilder.getInstance().create(Authentication::class.java)
}