package com.blank.ui.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.blank.ui.R

class EditTextPassword : AppCompatEditText,OnTouchListener {

    private lateinit var showHidePassword: Drawable
    private var isPasswordShow = false
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Masukkan Password Anda"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
    private fun init(){
        setOnTouchListener(this)
        checkVisiblePassword(false)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if( s.isNotEmpty() && s.length < 6){
                    error = " jumlah password kurang dari 6 karakter"
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })

    }

    private fun checkVisiblePassword(isShowPassword : Boolean){

     if (isShowPassword) {
         showHidePassword = ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_off_24) as Drawable
         transformationMethod = HideReturnsTransformationMethod.getInstance()
         setCompoundDrawablesWithIntrinsicBounds(null,null,showHidePassword,null)
        }
        else  {
         showHidePassword = ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_24) as Drawable
        transformationMethod = PasswordTransformationMethod.getInstance()
         setCompoundDrawablesWithIntrinsicBounds(null,null,showHidePassword,null)
        }
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (showHidePassword.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - showHidePassword.intrinsicWidth).toFloat()
                when {
                    event.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            return if (isClearButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        isPasswordShow = !isPasswordShow
                        checkVisiblePassword(isPasswordShow)
                        true
                    }
                    else -> false
                }
            } else false
        }
        return false
    }

}