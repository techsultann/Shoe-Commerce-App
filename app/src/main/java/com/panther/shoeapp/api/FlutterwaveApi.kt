package com.panther.shoeapp.api

import com.panther.shoeapp.models.api_response.PaymentRequest
import com.panther.shoeapp.models.api_response.PaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FlutterWaveApi {
    @POST("v3/payments")
    suspend fun flutterWavePayment(
        @Header("Authorization") token: String,
        @Body paymentRequest: PaymentRequest
    ) : Response<PaymentResponse>

    @GET("v3/transactions/:id}/verify")
    suspend fun verifyFlutterWavePayment(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        //@Body id: String
    ) : Response<PaymentResponse>

}