package com.panther.shoeapp.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode

object PaymentsUtil {

    val CENTS = BigDecimal(100)

    private val baseRequest = JSONObject()
        .put("apiVersion", 2)
        .put("apiVersionMinor", 0)

    private fun gatewayTokenizationSpecification(): JSONObject {
        return JSONObject().apply {
            put("type", "PAYMENT_GATEWAY")
            put("parameters", JSONObject(mapOf(
                "gateway" to "example",
                "gatewayMerchantId" to "exampleGatewayMerchantId")))
        }
    }

    private val allowedCardNetworks = JSONArray(listOf(
        "VERVE",
        "MASTERCARD",
        "VISA"))


    private val allowedCardAuthMethods = JSONArray(listOf(
        "PAN_ONLY",
        "CRYPTOGRAM_3DS"))


    private fun baseCardPaymentMethod(): JSONObject =
        JSONObject()
            .put("type", "CARD")
            .put(
                "parameters", JSONObject()
                    .put("allowedAuthMethods", allowedCardAuthMethods)
                    .put("allowedCardNetworks", allowedCardNetworks)
                    .put("billingAddressRequired", true)
                    .put(
                        "billingAddressParameters", JSONObject()
                            .put("format", "FULL")
                    )
            )

    val cardPaymentMethod: JSONObject = baseCardPaymentMethod()
        .put("tokenizationSpecification", gatewayTokenizationSpecification())


//    fun createPaymentsClient(context: Context): PaymentsClient {
//        val walletOptions = Wallet.WalletOptions.Builder()
//            .setEnvironment(Constants.PAYMENTS_ENVIRONMENT)
//            .build()
//
//        return Wallet.getPaymentsClient(context, walletOptions)
//    }

    fun isReadyToPayRequest(): JSONObject? =
        try {
            baseRequest
                .put("allowedPaymentMethods", JSONArray().put(baseCardPaymentMethod()))
        } catch (e: JSONException) {
            null
        }


    private fun getTransactionInfo(price: String): JSONObject =
        JSONObject()
            .put("totalPrice", price)
            .put("totalPriceStatus", "FINAL")
            .put("countryCode", Constants.COUNTRY_CODE)
            .put("currencyCode", Constants.CURRENCY_CODE)

    private val merchantInfo: JSONObject =
        JSONObject().put("merchantName", "Example Merchant")


    fun getPaymentDataRequest(priceCents: Double): JSONObject =
        baseRequest
            .put("allowedPaymentMethods", cardPaymentMethod)
            .put("transactionInfo", getTransactionInfo(priceCents.centsToString()))
            .put("merchantInfo", merchantInfo)
            .put("shippingAddressRequired", true)
            .put(
                "shippingAddressParameters", JSONObject()
                    .put("phoneNumberRequired", false)
                    .put("allowedCountryCodes", JSONArray(listOf("NG")))
            )

    private fun Double.centsToString() = BigDecimal(this)
        .divide(CENTS)
        .setScale(2, RoundingMode.HALF_EVEN)
        .toString()



}