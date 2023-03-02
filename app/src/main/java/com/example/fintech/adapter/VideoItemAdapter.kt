package com.example.fintech.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fintech.R
import com.example.fintech.model.VideosData


class VideoItemAdapter(
    var context: Context,
    var videos: List<VideosData>,
    var onAppLinkClick: AppLinkClick,
    var layout: String

) : RecyclerView.Adapter<VideoItemAdapter.RACItemHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RACItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.video_card_item, parent, false
        )
        var thumbnail = view.findViewById<ImageView>(R.id.thumbnail)
        when(layout){
            "first" -> thumbnail.setBackgroundResource(R.drawable.tutorial)
            "second" ->thumbnail.setBackgroundResource(R.drawable.australia_tour)
            "third" ->thumbnail.setBackgroundResource(R.drawable.popular)
        }
        this.context = parent.context
        return RACItemHolder(view)
    }

    override fun getItemCount() = videos.size

    inner class RACItemHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        val video_layout = view.findViewById<RelativeLayout>(R.id.video_card)
        var title = view.findViewById<TextView>(R.id.video_title)
        fun bindItem(videosData: VideosData) {
            title.text = videosData.title
            Log.e("VideoItemAdapter", videosData.video_url)
            Log.e("VideoItemAdapter", videosData.title)
            video_layout.setOnClickListener {
                Log.e("VideoItemAdapter", videosData.video_url)
                onAppLinkClick.onAppLinkClicked(videosData.video_url)
            }
        }
    }

    override fun onBindViewHolder(holder: RACItemHolder, position: Int) {
        holder.bindItem(videos[position])
    }

    interface AppLinkClick {
        fun onAppLinkClicked(videosData: String)
    }
}