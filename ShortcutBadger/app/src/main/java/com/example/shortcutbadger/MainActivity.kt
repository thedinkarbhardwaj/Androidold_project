package com.example.shortcutbadger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.leolin.shortcutbadger.ShortcutBadger


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val badgeCount = 1
        ShortcutBadger.applyCount(this, badgeCount) //for 1.1.4+

    }
}