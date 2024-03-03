package com.daedongyeojido.daedongyeojido_v1.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.daedongyeojido.daedongyeojido_v1.R

class BannerAdapter(private val bannerList: List<BannerData>) : Adapter<BannerAdapter.BannerViewHolder>() {
    private var listSize = bannerList.size
    inner class BannerViewHolder(itemView: View) : ViewHolder(itemView) {
        val banner: ImageView = itemView.findViewById(R.id.img_home_item_banner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val imgUrl = bannerList[position % listSize].bannerImgUrl
        Glide.with(holder.banner.context)
            .load(imgUrl)
            .into(holder.banner)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE
}