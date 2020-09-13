package com.arpansircar.currencyconverterapplication.model

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class DownloadInfoClass {

    private var mutableLiveData: MutableLiveData<String> = MutableLiveData()

    fun downloadMethod(urls: String) = CoroutineScope(Main).launch {
        var result = ""

        withContext(IO){
            try{
                val url = URL(urls)
                val httpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                var data: Int = inputStreamReader.read()
                while (data != -1){
                    val current = data.toChar().toString()
                    result += current
                    data = inputStreamReader.read()
                }
                mutableLiveData.postValue(result)
            }

            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun getMutableLiveData(): MutableLiveData<String>{
        return mutableLiveData
    }
}