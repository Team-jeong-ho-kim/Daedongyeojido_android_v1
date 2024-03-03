package com.daedongyeojido.daedongyeojido_v1.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.daedongyeojido.daedongyeojido_v1.MainActivity
import com.daedongyeojido.daedongyeojido_v1.R
import com.daedongyeojido.daedongyeojido_v1.databinding.ItemHomeRecyclerBinding

class HomeClubAdapter(private val clubList: List<HomeClubData>, private val clubClickListener: ClubClickListener) : Adapter<HomeClubAdapter.HomeClubViewHolder>() {
    inner class HomeClubViewHolder(private val binding: ItemHomeRecyclerBinding) : ViewHolder(binding.root) {
        fun bind(clubData: HomeClubData) {
            Glide.with(binding.imgHomeItemLogo.context).load(clubData.clubImageUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20))).into(binding.imgHomeItemLogo)
            binding.textHomeItemName.text = clubData.clubName
            binding.textHomeItemExplain.text = clubData.title
            for (tag in clubData.tags) {
                val tagView = TextView(binding.root.context)
                tagView.text = tag
                tagView.setTextAppearance(R.style.text_item_tag)
                binding.layHomeItemTag.addView(tagView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeClubViewHolder {
        val binding = ItemHomeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeClubViewHolder(binding)
    }

    override fun getItemCount(): Int = clubList.size
    override fun onBindViewHolder(holder: HomeClubViewHolder, position: Int) {
        holder.bind(clubList[position])
        holder.itemView.setOnClickListener {
            // MainActivity().clubDetail(clubList[position].clubName)
            clubClickListener.onClubClicked(clubList[position].clubName)
        }
    }

    interface ClubClickListener {
        fun onClubClicked(clubName: String)
    }
}