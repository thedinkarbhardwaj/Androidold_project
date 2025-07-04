package com.example.getstream

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import io.getstream.client.Client
import io.getstream.core.http.Token
import io.getstream.core.models.Activity
import io.getstream.core.options.Pagination


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var client = Client.builder(
            "9he36sms3k9q",
            "yhmz7xmpggnsh7k96bk4zzrur8nw8syc6sbd99zvwfjxva5s22qjbj7pgf3u4bkj"
        ).build();


        val userToken: Token = client.frontendToken("1")


        val chris = client.flatFeed("user", "1")
// Add an Activity; message is a custom field - tip: you can add unlimited custom fields!
// Add an Activity; message is a custom field - tip: you can add unlimited custom fields!
        chris.addActivity(
            Activity.builder()
                .actor("chris")
                .verb("add")
                .`object`("picture:10")
                .foreignID("picture:10")
                .extraField("message", "Beautiful bird!")
                .build()
        )

// Create a following relationship between Jack's "timeline" feed and Chris' "user" feed:

// Create a following relationship between Jack's "timeline" feed and Chris' "user" feed:
        val jack = client.flatFeed("timeline", "jack")
        jack.follow(chris).join()

// Read Jack's timeline and Chris' post appears in the feed:

// Read Jack's timeline and Chris' post appears in the feed:
        val response: List<Activity> = jack.getActivities(Pagination().limit(10)).join()
        for (activity in response) {
            Log.d("ActivityResponse",response.toString())
        }

        Log.d("ActivityResponse2",response.toString())


// Remove an Activity by referencing it's foreign_id

// Remove an Activity by referencing it's foreign_id
        chris.removeActivityByForeignID("picture:10").join()

    }
}