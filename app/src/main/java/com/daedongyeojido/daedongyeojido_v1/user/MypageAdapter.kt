package com.daedongyeojido.daedongyeojido_v1.user

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.daedongyeojido.daedongyeojido_v1.MainActivity
import com.daedongyeojido.daedongyeojido_v1.R
import com.daedongyeojido.daedongyeojido_v1.databinding.ItemMyRecyclerBinding

class MypageAdapter(private val reportList: List<ReportData>) : Adapter<MypageAdapter.MypageViewHolder>() {
    inner class MypageViewHolder(private val binding: ItemMyRecyclerBinding) : ViewHolder(binding.root) {
        fun bind(reportData: ReportData) {
            binding.textMyItemName.text = reportData.clubName
            binding.textMyItemMajor.text = reportData.hopeMajor
            binding.textMyItemDeadline.text = reportData.deadline
            btnSetting(binding.btnMyItemState, reportData.passingResult)
            binding.textMyItemMajor.text = MainActivity().setMajor(reportData.hopeMajor)
        }
        private fun btnSetting(btn: AppCompatButton, result: String) {
            when(result) {
                "PASS" -> {
                    btn.text = "합격"
                    btn.setBackgroundColor(Color.parseColor(R.color.main.toString()))
                }
                "FAIL" -> {
                    btn.text = "불합격"
                    btn.setBackgroundColor(Color.parseColor(R.color.button_gray.toString()))
                }
                "WAIT" -> {
                    btn.text = "대기 중"
                    btn.setBackgroundColor(Color.parseColor(R.color.light_gray.toString()))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypageViewHolder {
        val binding = ItemMyRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MypageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MypageViewHolder, position: Int) {
        holder.bind(reportList[position])
    }

    override fun getItemCount(): Int = reportList.size
}