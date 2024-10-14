package com.example.simplecalculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel: ViewModel() {

    private val _equationData = MutableLiveData("")
    val equationData : LiveData<String> = _equationData

    private val _resultData = MutableLiveData("")
    val resultData : LiveData<String> = _resultData

    fun onButtonCLick(btn: String) {
        Log.i("Clicked Button" , btn)

        _equationData.value?.let {
            if(btn == "AC"){
                _equationData.value = ""
                _resultData.value = "0"
                return
            }
            if(btn == "C"){
                if(it.isNotEmpty()){
                    _equationData.value = it.substring(0,it.length-1)
                }
                return
            }
            if(btn == "="){
                _equationData.value = _resultData.value
                return
            }
            _equationData.value = it+btn

            //Calculation
            Log.i("Equation",_equationData.value.toString())
            try {
                _resultData.value = calculateResult(_equationData.value.toString())
            } catch (_ : Exception){

            }
        }
    }

    fun calculateResult (equation:String) : String{
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable,equation,"Javascript",1,null).toString()
        if(finalResult.endsWith(".0")){
            finalResult = finalResult.replace(".0","")
        }
        return finalResult
    }
}














