package com.daedongyeojido.daedongyeojido_v1.club.notice

import android.app.AlertDialog.Builder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.daedongyeojido.daedongyeojido_v1.CustomDialog
import com.daedongyeojido.daedongyeojido_v1.MainActivity
import com.daedongyeojido.daedongyeojido_v1.R
import com.daedongyeojido.daedongyeojido_v1.club.NoticeData
import com.daedongyeojido.daedongyeojido_v1.databinding.ItemClubNoticeRecyclerBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ClubNoticeAdapter(private val noticeList: List<NoticeData>, private val clubNoticeClickListener: ClubNoticeClickListener) : Adapter<ClubNoticeAdapter.ClubNoticeViewHolder>() {
    inner class ClubNoticeViewHolder(private val binding: ItemClubNoticeRecyclerBinding) : ViewHolder(binding.root) {
        val button = binding.btnClubNotice
        fun bind(noticeData: NoticeData) {
            binding.textClubNoticeMajor.text = MainActivity().setMajor(noticeData.major)
            binding.textClubNoticeDay.text = noticeData.deadline
            setButton(binding.btnClubNotice, noticeData.deadline, noticeData.applyOrNot)
        }
        private fun setButton(btn: AppCompatButton, deadline: String, applyOrNot: Boolean) {
            if (applyOrNot) {
                btn.text = "취소하기"
                btn.setBackgroundResource(R.drawable.btn_after)
            } else {
                val today = Calendar.getInstance()
                val sf = SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.KOREA)
                val date = sf.parse(deadline)
                val day = ((date?.time ?: 0) - today.time.time) / (60 * 60 * 24 * 1000)
                if (day >= 0) {
                    btn.text = "지원하기"
                    btn.setBackgroundResource(R.drawable.btn_after)
                } else {
                    btn.text = "지원 마감"
                    btn.setBackgroundResource(R.drawable.btn_before)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubNoticeViewHolder {
        val binding = ItemClubNoticeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClubNoticeViewHolder(binding)
    }
    override fun getItemCount(): Int = noticeList.size
    override fun onBindViewHolder(holder: ClubNoticeViewHolder, position: Int) {
        holder.bind(noticeList[position])
        holder.button.setOnClickListener {
            if (holder.button.text == "지원하기") {
                clubNoticeClickListener.onClubNoticeClicked(noticeList[position].clubName, noticeList[position].major)
            } else if (holder.button.text == "취소하기") {
                val content = MainActivity().setMajor(noticeList[position].major) + " 지원을 취소하시겠습니까?"
                clubNoticeClickListener.onNoticeCancelClicked("Notice", noticeList[position].id, noticeList[position].clubName, content, "취소하기", "돌아가기")
            }
        }
    }

    interface ClubNoticeClickListener {
        fun onClubNoticeClicked(clubName: String, major: String)
        fun onNoticeCancelClicked(type: String, id: Long, title: String, content: String, accept: String, cancel: String)
    }
}