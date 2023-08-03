package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.btn_0
import kotlinx.android.synthetic.main.activity_main.btn_1
import kotlinx.android.synthetic.main.activity_main.btn_2
import kotlinx.android.synthetic.main.activity_main.btn_3
import kotlinx.android.synthetic.main.activity_main.btn_5
import kotlinx.android.synthetic.main.activity_main.btn_6
import kotlinx.android.synthetic.main.activity_main.btn_8
import kotlinx.android.synthetic.main.activity_main.btn_9
import kotlinx.android.synthetic.main.activity_main.btn_4
import kotlinx.android.synthetic.main.activity_main.btn_7
import kotlinx.android.synthetic.main.activity_main.btn_leftparantheses
import kotlinx.android.synthetic.main.activity_main.btn_rightparantheses
import kotlinx.android.synthetic.main.activity_main.delete
import kotlinx.android.synthetic.main.activity_main.devide
import kotlinx.android.synthetic.main.activity_main.equal
import kotlinx.android.synthetic.main.activity_main.eraseAll
import kotlinx.android.synthetic.main.activity_main.minus
import kotlinx.android.synthetic.main.activity_main.multiple
import kotlinx.android.synthetic.main.activity_main.plus
import kotlinx.android.synthetic.main.activity_main.point
import kotlinx.android.synthetic.main.activity_main.result

class MainActivity : AppCompatActivity() {
    var GLOBAL_RESULT = 0.0
    var CURRENT_OPERATION = ""
    var SECOND_OPERAND = 0.0
    var OPERATION_CLICKED = false
    var TYPING = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButtons()
    }

    fun initButtons() {
        btn_1.setOnClickListener {
            clickDigit("1")
        }
        btn_2.setOnClickListener {
            clickDigit("2")
        }
        btn_3.setOnClickListener {
            clickDigit("3")
        }
        btn_4.setOnClickListener {
            clickDigit("4")
        }
        btn_5.setOnClickListener {
            clickDigit("5")
        }
        btn_6.setOnClickListener {
            clickDigit("6")
        }
        btn_7.setOnClickListener {
            clickDigit("7")
        }
        btn_8.setOnClickListener {
            clickDigit("8")
        }
        btn_9.setOnClickListener {
            clickDigit("9")
        }
        btn_0.setOnClickListener {
            clickDigit("0")
        }
        delete.setOnClickListener {
            removeDigit()
        }

        plus.setOnClickListener {
            operationClicked("+")

        }
        minus.setOnClickListener {
            operationClicked("-")
        }
        multiple.setOnClickListener {
            operationClicked("*")
        }
        devide.setOnClickListener {
            operationClicked("/")
        }
        equal.setOnClickListener {
            calcResult()
        }
        eraseAll.setOnClickListener {
            reset()
        }

        point.setOnClickListener {
            addPoint()
        }

        btn_leftparantheses.setOnClickListener {
            parantheses("(")
        }

        btn_rightparantheses.setOnClickListener {
            parantheses(")")
        }

    }


    fun clickDigit(numberClicked: String) {
        if (!OPERATION_CLICKED && !TYPING) {
            result.text = numberClicked // in case we did an operation then we clicked a digit
            GLOBAL_RESULT = 0.0
        }

        else if (result.text.toString() == "0") { // no operation searching for first operand
            result.text = numberClicked
        }

        else if (OPERATION_CLICKED) {  // in case of +++++
            OPERATION_CLICKED = false
            result.text = numberClicked
        }

        else if (result.text=="ERROR"){
            reset()
        }
        else {
            result.text = "${result.text}${numberClicked}"
        }
        TYPING = true

    }

    fun operationClicked(operation: String) {
        if(result.text!="ERROR"){
            if(CURRENT_OPERATION==""){ // In case we didn't click any operation
                CURRENT_OPERATION = operation
                OPERATION_CLICKED = true
                if(GLOBAL_RESULT==0.0){
                    GLOBAL_RESULT=result.text.toString().toDouble()
                }else{
                    calcResult()
                    CURRENT_OPERATION=""
                }
                TYPING=false
            }else{ // in case we clicked before an operation and we clicked new one we have to calculate the previous operation before going to the next op

                OPERATION_CLICKED = true
                if(GLOBAL_RESULT==0.0){
                    GLOBAL_RESULT=result.text.toString().toDouble()
                }else{
                    calcResult()
                    CURRENT_OPERATION = operation
                }
                TYPING=false
            }

        }


    }

    fun calcResult() {

        if (CURRENT_OPERATION == "+") {
            SECOND_OPERAND = result.text.toString().toDouble()
            if (TYPING) {
                GLOBAL_RESULT += SECOND_OPERAND
                result.text = "${GLOBAL_RESULT}"
                TYPING = false
            }

        }else if (CURRENT_OPERATION== "-"){
            SECOND_OPERAND = result.text.toString().toDouble()
            if (TYPING) {
                GLOBAL_RESULT -= SECOND_OPERAND
                result.text = "${GLOBAL_RESULT}"
                TYPING = false
            }
        }else if (CURRENT_OPERATION=="/"){
            SECOND_OPERAND = result.text.toString().toDouble()
            if (TYPING) {
                if(SECOND_OPERAND==0.0){
                    result.text = "ERROR"
                    GLOBAL_RESULT=0.0
                }else{
                    GLOBAL_RESULT /= SECOND_OPERAND
                    result.text = "${GLOBAL_RESULT}"
                    TYPING = false
                }

            }
        }else if (CURRENT_OPERATION=="*"){
            SECOND_OPERAND = result.text.toString().toDouble()
            if (TYPING) {
                GLOBAL_RESULT *= SECOND_OPERAND
                result.text = "${GLOBAL_RESULT}"
                TYPING = false
            }
        }



    }

    fun reset() {
        TYPING=false
        OPERATION_CLICKED=false
        GLOBAL_RESULT=0.0
        CURRENT_OPERATION=""
        result.text="0"
    }

    fun removeDigit(){
        if(result.text!="ERROR"){
            if(TYPING){
                if (result.text.toString().length > 1) {
                    result.text = result.text.subSequence(0, result.text.toString().length - 1)
                } else {
                    result.text = "0"
                }
            }

        }

    }
    fun addSign(){
        if(result.text!="ERROR"){
            if(result.text.toString()!="0"){
                if(result.text.toString()[0]=='-'){
                    result.text=result.text.toString().replace("-","")
                }else{
                    result.text = "-${result.text}"
                    if(GLOBAL_RESULT!=0.0){
                        GLOBAL_RESULT=-GLOBAL_RESULT
                    }

                }
            }
        }


    }
    fun addPoint(){
        if(result.text!="ERROR"){
            if(!result.text.toString().contains(".")){
                result.text = "${result.text}."
            }
        }


    }

    fun parantheses(paranthases: String) {
        if (result.text == "0") {
            result.text = "${paranthases}"
        } else {
            result.text = "${result.text} ${paranthases}"
        }

    }

}