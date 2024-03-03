package com.example.openexchange.helper


import com.example.openexchange.network.model.ExchangeSchema
import kotlinx.serialization.json.Json
import java.io.File

object TestData {
    val RATES_SERVER_RESPONSE_JSON_SUCCESS: String = getJson("success_rates.json")
    val CURRENCY_SERVER_RESPONSE_JSON_SUCCESS: String = getJson("success_currency.json")

    val SERVER_RESPONSE_EXCHANGE_SCHEMA: ExchangeSchema =
        Json.decodeFromString(RATES_SERVER_RESPONSE_JSON_SUCCESS)

    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */
    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}