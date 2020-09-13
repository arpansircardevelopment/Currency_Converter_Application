package com.arpansircar.currencyconverterapplication.model

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat

class JSONParserClass {
    private val mutableLiveData: MutableLiveData<MutableMap<String, Any>> = MutableLiveData()
    private var currencyMutableMap: MutableMap<String, Any> = mutableMapOf()

    fun getMethod(downloadedJSON: String) = CoroutineScope(Main).launch{
        val mainJSONObject = JSONObject(downloadedJSON)
        val ratesJSONObject = JSONObject(mainJSONObject.getJSONObject("rates").toString())

        withContext(Default){
            try {
                currencyMutableMap["date"] = mainJSONObject.getString("date")
                currencyMutableMap["USD"] = roundOffMethod(ratesJSONObject.getString("USD").toDouble())
                currencyMutableMap["GBP"] = roundOffMethod(ratesJSONObject.getString("GBP").toDouble())
                currencyMutableMap["EUR"] = roundOffMethod(ratesJSONObject.getString("EUR").toDouble())
                currencyMutableMap["CHF"] = roundOffMethod(ratesJSONObject.getString("CHF").toDouble())
                currencyMutableMap["SEK"] = roundOffMethod(ratesJSONObject.getString("SEK").toDouble())
                currencyMutableMap["CAD"] = roundOffMethod(ratesJSONObject.getString("CAD").toDouble())
                mutableLiveData.postValue(currencyMutableMap)
            }

            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun roundOffMethod(value: Double): Double{
        val decimalFormat = DecimalFormat("#.###")
        decimalFormat.roundingMode = RoundingMode.HALF_DOWN
        return (decimalFormat.format(value)).toDouble()
    }

    fun getMutableLiveData(): MutableLiveData<MutableMap<String, Any>>{
        return mutableLiveData
    }
}