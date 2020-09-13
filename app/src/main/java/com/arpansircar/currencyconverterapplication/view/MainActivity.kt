package com.arpansircar.currencyconverterapplication.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arpansircar.currencyconverterapplication.R
import com.arpansircar.currencyconverterapplication.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var currencyEditText: EditText
    private lateinit var convertButton: Button
    private lateinit var dateInfoTextView: TextView
    private lateinit var usdTextView: TextView
    private lateinit var gbpTextView: TextView
    private lateinit var eurTextView: TextView
    private lateinit var chfTextView: TextView
    private lateinit var sekTextView: TextView
    private lateinit var cadTextView: TextView

    private var valuesMap: MutableMap<String, Any> = mutableMapOf()
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarMethod()
        initViews()
        triggerAPIMethod()
    }

    override fun onResume(){
        super.onResume()
        convertButton.setOnClickListener(this)
    }

    private fun plugMethod(values: MutableMap<String, Any>){
        dateInfoTextView.text = getString(R.string.date_info_string, values["date"].toString())
        usdTextView.text = getString(R.string.usd_value_string, values["USD"].toString())
        gbpTextView.text = getString(R.string.gbp_value_string, values["GBP"].toString())
        eurTextView.text = getString(R.string.eur_value_string, values["EUR"].toString())
        chfTextView.text = getString(R.string.chf_value_string, values["CHF"].toString())
        sekTextView.text = getString(R.string.sek_value_string, values["SEK"].toString())
        cadTextView.text = getString(R.string.cad_value_string, values["CAD"].toString())
    }

    private fun initViews(){
        currencyEditText = findViewById(R.id.currency_editText)
        dateInfoTextView = findViewById(R.id.date_info_textView)
        convertButton = findViewById(R.id.convert_button)
        usdTextView = findViewById(R.id.usd_textView)
        gbpTextView = findViewById(R.id.gbp_textView)
        eurTextView = findViewById(R.id.eur_textView)
        chfTextView = findViewById(R.id.chf_textView)
        sekTextView = findViewById(R.id.sek_textView)
        cadTextView = findViewById(R.id.cad_textView)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private fun triggerAPIMethod(){
        mainActivityViewModel.downloadAPIMethod().observe(this, Observer { triggerJSONParser(it) })
    }

    private fun triggerJSONParser(downloadedJSON: String){
        mainActivityViewModel.parseJSONMethod(downloadedJSON).observe(this, Observer {
            valuesMap = it
            plugMethod(it)
        })
    }

    override fun onClick(v: View?){
        hideKeyboardMethod()
        val enteredText: Double = currencyEditText.text.toString().toDouble()
        mainActivityViewModel.convertCurrencyMethod(enteredText, valuesMap).observe(this, Observer { plugMethod(it) })
    }

    private fun hideKeyboardMethod(){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currencyEditText.windowToken, 0)
    }

    private fun setActionBarMethod(){
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setCustomView(R.layout.layout_action_bar)
    }
}