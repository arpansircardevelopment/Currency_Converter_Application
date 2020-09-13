package com.arpansircar.currencyconverterapplication.model

import androidx.lifecycle.MutableLiveData

class ConvertCurrencyClass (values: MutableMap<String, Any>){
    private var valuesMap: MutableMap<String, Any> = mutableMapOf()
    private var convertedValuesMap: MutableMap<String, Any> = mutableMapOf()
    private var valuesMutableLiveData: MutableLiveData<MutableMap<String, Any>> = MutableLiveData()

    init {
        valuesMap = values
    }

    fun convertMethod(value: Double){
        val jsonParserClass = JSONParserClass()
        for (i in valuesMap.keys){
            if (i == "date")
                convertedValuesMap[i] = valuesMap[i].toString()

            else{
                val currentValue = valuesMap[i].toString().toDouble()
                convertedValuesMap[i] = jsonParserClass.roundOffMethod(currentValue * value)
            }
        }
        valuesMutableLiveData.postValue(convertedValuesMap)
    }

    fun getMutableMapMethod(): MutableLiveData<MutableMap<String, Any>>{
        return valuesMutableLiveData
    }
}