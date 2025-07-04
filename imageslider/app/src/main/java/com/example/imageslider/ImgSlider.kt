package com.example.imageslider


import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import java.util.ArrayList


class ImgSlider : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_slider)

        val sliderView = findViewById<SliderView>(R.id.imageSlider)

        var list = listOf(
            imgsliderrrrdata(R.drawable.team, "Title 1", "Description 1"),
            imgsliderrrrdata(R.drawable.policy, "Title 2", "Description 2"),
            imgsliderrrrdata(R.drawable.report, "Title 3", "Description 3")
        )
//        val adapter = SliderAdapterExample(this)
        val adpt = SliderAdapterExample(
            this@ImgSlider,
            list
        )
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(adpt);
        sliderView.scrollTimeInSec = 5
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        sliderView.setSliderAdapter(adpt)

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView.indicatorSelectedColor = Color.WHITE
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 4 //set scroll delay in seconds :

        sliderView.startAutoCycle()
    }

}