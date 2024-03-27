package com.daedongyeojido.daedongyeojido_v1.club.qna

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.daedongyeojido.daedongyeojido_v1.club.QnAData
import com.daedongyeojido.daedongyeojido_v1.databinding.ItemClubQnaRecyclerBinding

class ClubQnaAdapter(private val qnaList: List<QnAData>) : Adapter<ClubQnaAdapter.ClubQnaViewHolder>() {
    inner class ClubQnaViewHolder(private val binding: ItemClubQnaRecyclerBinding) : ViewHolder(binding.root) {
        val answerView = binding.textQnaItemAnswer
        fun bind(qnAData: QnAData) {
            binding.textQnaItemQuestion.text = qnAData.question
            binding.textQnaItemAnswer.text = qnAData.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubQnaViewHolder {
        val binding = ItemClubQnaRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClubQnaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClubQnaViewHolder, position: Int) {
        holder.bind(qnaList[position])
        holder.itemView.setOnClickListener {
            if (holder.answerView.visibility == GONE) {
                holder.answerView.visibility = VISIBLE
            } else {
                holder.answerView.visibility = GONE
            }
        }
    }
    override fun getItemCount(): Int = qnaList.size
}