package com.soccerpo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soccerpo.data.db.entity.Videos
import com.soccerpo.databinding.ItemSoccerVideoBinding
import com.soccerpo.utils.popUpAds
import com.soccerpo.utils.toast

class VideoAdapter(
    private val context: Context,
    private val listener: onItemClicked
) : RecyclerView.Adapter<VideoAdapter.VideoVH>() {
    private var oldVideoData = emptyList<Videos>()
    private var isActivo = false
    class VideoVH(val binding: ItemSoccerVideoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.VideoVH {
        return VideoVH(ItemSoccerVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VideoAdapter.VideoVH, position: Int) {
        val video = oldVideoData?.get(position)

        holder.binding.itemVideoDate.text = video?.date
        Glide.with(context).load(video?.thumbnail).into(holder.binding.dashWebview)

        holder.binding.dashWebview.setOnClickListener {
            listener.onItemClicked(context,"${video?.video}")
        }
//        if(isActivo){
//
//        }else{
//            context.toast("Something Went Wrong $isActivo")
//        }

    }

    override fun getItemCount(): Int {
        return oldVideoData.size
    }

    fun setVideoData(newVideoData: List<Videos>){
        oldVideoData = newVideoData
        notifyDataSetChanged()
    }

    fun setVideoActivo(activo:Boolean){
        isActivo = activo
    }

    interface onItemClicked {
        fun onItemClicked(context: Context, message: String)
        fun onClickedImage(context: Context, url: String)
    }

//    dash_webview
//    item_video_date
//    item_views
//    item_like
}