package com.example.razorpayintegration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var puzzleGridLayout: GridLayout
    private val puzzlePieces = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        puzzleGridLayout = findViewById(R.id.puzzleGridLayout)

        // Add puzzle pieces to the list
        for (i in 1..puzzleGridLayout.childCount) {
            val piece = puzzleGridLayout.getChildAt(i - 1) as ImageView
            piece.setOnClickListener { onPieceClick(piece) }
            puzzlePieces.add(piece)
        }

        // Shuffle the puzzle pieces
        shufflePuzzle()

    }


    private fun onPieceClick(piece: ImageView) {
        // Handle clicks on puzzle pieces (e.g., swap pieces)
        // You can implement logic to swap the clicked piece with an adjacent empty space.
    }

    private fun shufflePuzzle() {
        puzzlePieces.shuffle()
        puzzleGridLayout.removeAllViews()


        for (i in 0 until puzzleGridLayout.childCount) {
            val piece = puzzlePieces[i]
            puzzleGridLayout.addView(piece)
        }
    }

}