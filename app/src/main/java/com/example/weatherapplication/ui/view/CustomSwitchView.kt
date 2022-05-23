package com.example.weatherapplication.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ViewCustomSwitchBinding

/**
 * @author Kalmykova Natalia
 */
class CustomSwitchView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private val firstItem: CustomSwitchItem
    private val secondItem: CustomSwitchItem

    var selectedValue: String = ""
        set(value) {
            field = value
            if (value == firstItem.text) {
                firstItem.isChosen = true
                secondItem.isChosen = false
            } else {
                firstItem.isChosen = false
                secondItem.isChosen = true
            }
        }

    private val binding =
        ViewCustomSwitchBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        firstItem = binding.firstItem
        secondItem = binding.secondItem

        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomSwitchView, 0, 0
        ).apply {
            val firstItemText = getString(R.styleable.CustomSwitchView_firstItemText)
            val secondItemText = getString(R.styleable.CustomSwitchView_secondItemText)

            firstItem.text = firstItemText
            secondItem.text = secondItemText
        }

        binding.root.setOnClickListener {
            changeSelectedValue()
        }
    }

    private fun changeSelectedValue() {
        selectedValue =
            if (selectedValue == firstItem.text) {
                secondItem.text.toString()
            } else {
                firstItem.text.toString()
            }
    }
}