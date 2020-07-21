package com.example.colormyviews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    fun setListeners() {
        val clickableViews: List<View> =
            listOf(
                box_one_text, box_two_text, box_three_text,
                box_four_text, box_five_text, constraint_layout
            )
        for (item in clickableViews) {
            item.setOnClickListener { makeColored(it) }
        }
    }

    private fun makeColored(view: View) {
        if (view.id != null) {

            // Boxes using Color class colors for the background
            box_one_text.setBackgroundColor(Color.DKGRAY)
            box_two_text.setBackgroundColor(Color.GRAY)
            box_three_text.setBackgroundColor(Color.BLUE)
            box_four_text.setBackgroundColor(Color.MAGENTA)
            box_five_text.setBackgroundColor(Color.BLUE)
        }
        else{
            view.setBackgroundColor(Color.LTGRAY)
        }
    }
}