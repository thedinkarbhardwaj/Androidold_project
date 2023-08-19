package com.example.motionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet.Motion

class MainActivity : AppCompatActivity() {
    lateinit var motionlayout:MotionLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        motionlayout = findViewById(R.id.motionlayout)

        motionlayout.startLayoutAnimation()

        motionlayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                Toast.makeText(this@MainActivity,"Start",Toast.LENGTH_LONG).show()
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                Toast.makeText(this@MainActivity,"change",Toast.LENGTH_LONG).show()

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                Toast.makeText(this@MainActivity,"Completed",Toast.LENGTH_LONG).show()

            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                Toast.makeText(this@MainActivity,"Trigger",Toast.LENGTH_LONG).show()

            }

        })
    }
}