package com.soccerpo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soccerpo.R
import com.soccerpo.data.db.entity.Soccer
import com.soccerpo.data.db.entity.SoccerChinese
import com.soccerpo.databinding.ItemSoccerNewsBinding
import com.soccerpo.databinding.ItemSoccerVideoBinding
import com.soccerpo.ui.home.HomeListener

class MultiAdapter (
    private val context: Context,
    private val type: Int
) : RecyclerView.Adapter<MultiAdapter.MyViewHolder>() {

    private var oldData = emptyList<Soccer>()
    private var language = "en"

    companion object {
        const val VIEW_TYPE_ONE = 0
        const val VIEW_TYPE_TWO = 1
    }

    class MyViewHolder(val binding: ItemSoccerNewsBinding ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSoccerNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = oldData?.get(position)

        if (language == "en") {
            holder.binding.itemHorseNewsTitle.text = item?.title
            holder.binding.itemHorsNewsDescription.text = item?.description
            Glide.with(context).load(item?.imageUrl).into(holder.binding.itemHorseNewsImage)
        }

        if (language == "zh") {
            holder.binding.itemHorseNewsTitle.text = item?.titleChinese
            holder.binding.itemHorsNewsDescription.text = item?.descriptionChinese
            Glide.with(context).load(item?.imageUrl).into(holder.binding.itemHorseNewsImage)
        }

    }

    override fun getItemCount(): Int {
        return oldData.size
    }

    fun setIsChinese(changeLanguage: String){
        language = changeLanguage
        notifyDataSetChanged()
    }

    fun setData(newData: List<Soccer>){
        oldData = newData
        notifyDataSetChanged()
    }
}


//        webview.apply {
//            this.loadUrl(url)
//            settings.javaScriptEnabled = true
//        }
