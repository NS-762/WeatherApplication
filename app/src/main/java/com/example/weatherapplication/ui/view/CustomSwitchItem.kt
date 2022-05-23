package com.example.weatherapplication.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatherapplication.R

/**
 * @author Kalmykova Natalia
 */
class CustomSwitchItem(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    var isChosen: Boolean = false
        set(value) {
            if (value) {
                changeColors(R.drawable.gradient, R.color.white)
            } else {
                changeColors(R.color.argent, R.color.sonic_silver)
            }
            field = value
        }

    private fun changeColors(backgroundRes: Int, textColorRes: Int) {
        val backgroundDrawable = resources.getDrawable(backgroundRes, null)
        val textColor = resources.getColor(textColorRes, null)

        background = backgroundDrawable
        setTextColor(textColor)
    }
}