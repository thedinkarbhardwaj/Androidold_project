package com.example.imageslider

import ViewPagerImageAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var dotsLayout: LinearLayout
    private val images = arrayOf(R.drawable.policy, R.drawable.report, R.drawable.team)
    private lateinit var dots: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        dotsLayout = findViewById(R.id.dotsLayout)

        addDots()
        val viewPagerImageAdapter = ViewPagerImageAdapter(this, images)
        viewPager.adapter = viewPagerImageAdapter
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
    }

    private fun addDots() {
        dots = Array(images.size) { ImageView(this) }

        for (i in images.indices) {
            dots[i].setImageResource(R.drawable.team)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 0, 8, 0)
                gravity = Gravity.CENTER
            }

            dotsLayout.addView(dots[i], params)
        }

        dots[0].setImageResource(R.drawable.mstrid) // dot active
    }

    private val viewPagerPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            dots.forEach { it.setImageResource(R.drawable.team) }
            dots[position].setImageResource(R.drawable.mstrid)
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }
}
