package com.example.videoandimage

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideoPagerAdapter(var activity: MainActivity, private val videoItems: List<VideoItem>) :
    RecyclerView.Adapter<VideoPagerAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoItem = videoItems[position]

        // Load image using Picasso (or your preferred image loading library)
     //   Picasso.get().load(videoItem.imageUrl).into(holder.imageView)

        if (videoItem.description == "img"){
            holder.videoview.visibility = View.GONE
            holder.imageView.visibility = View.VISIBLE

            Glide.with(activity).load(videoItem.imageUrl).into(holder.imageView)

        }else{

            val videoView: VideoView = holder.videoview
            val videoUrl = "https://phpstack-102119-3888765.cloudwaysapps.com/public/uploads/lesson_img_vid/video_1697606240.mp4"

            val mediaController = MediaController(activity)
            mediaController.setAnchorView(holder.videoview)

            videoView.setMediaController(mediaController)
            videoView.setVideoURI(Uri.parse(videoUrl))

// Set up a listener to start playing when the video is prepared
            videoView.setOnPreparedListener { mp ->
                mp.start()
            }


        }

        holder.titleTextView.text = videoItem.title
        holder.descriptionTextView.text = videoItem.description
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.thumbnailImageView)
        val videoview: VideoView = itemView.findViewById(R.id.videoview)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }
}
