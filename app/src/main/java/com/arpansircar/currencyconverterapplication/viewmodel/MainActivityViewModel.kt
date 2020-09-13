package com.arpansircar.currencyconverterapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arpansircar.currencyconverterapplication.model.ConvertCurrencyClass
import com.arpansircar.currencyconverterapplication.model.DownloadInfoClass
import com.arpansircar.currencyconverterapplication.model.JSONParserClass

class MainActivityViewModel: ViewModel() {

    fun downloadAPIMethod(): MutableLiveData<String>{
        val downloadInfoClass = DownloadInfoClass()
        downloadInfoClass.downloadMethod("https://api.exchangeratesapi.io/latest?base=INR&api_key=6b8f07dc9dc2f1208012")
        return downloadInfoClass.getMutableLiveData()
    }

    fun parseJSONMethod(downloadedJSON: String): MutableLiveData<MutableMap<String, Any>>{
        val jsonParserClass = JSONParserClass()
        jsonParserClass.getMethod(downloadedJSON)
        return jsonParserClass.getMutableLiveData()
    }

    fun convertCurrencyMethod(enteredText: Double, valuesMap: MutableMap<String, Any>): MutableLiveData<MutableMap<String, Any>> {
        val convertCurrencyClass = ConvertCurrencyClass(valuesMap)
        convertCurrencyClass.convertMethod(enteredText)
        return convertCurrencyClass.getMutableMapMethod()
    }

}