package com.daedongyeojido.daedongyeojido_v1.club.notice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.daedongyeojido.daedongyeojido_v1.ApiProvider
import com.daedongyeojido.daedongyeojido_v1.ServerApi
import com.daedongyeojido.daedongyeojido_v1.Token
import com.daedongyeojido.daedongyeojido_v1.club.NoticeData
import com.daedongyeojido.daedongyeojido_v1.databinding.FragmentClubNoticeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClubNoticeFragment(private val clubName: String) : Fragment() {
    private lateinit var binding: FragmentClubNoticeBinding
    private val noticeList = mutableListOf<NoticeData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClubNoticeBinding.inflate(layoutInflater, container, false)
        getNoticeData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noticeAdapter = ClubNoticeAdapter(noticeList)
        binding.recyclerClubNotice.adapter = noticeAdapter
        binding.recyclerClubNotice.layoutManager = LinearLayoutManager(activity)
    }

    private fun getNoticeData() {
        val apiProvider = ApiProvider.getInstance().create(ServerApi::class.java)
        apiProvider.getNotice(clubName, Token().getAccessToken()).enqueue(object : Callback<List<NoticeData>> {
            override fun onResponse(call: Call<List<NoticeData>>, response: Response<List<NoticeData>>) {
                if (response.isSuccessful) {
                    response.body()?.let { setNoticeData(it) }
                } else {
                    Log.d("server", response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<NoticeData>>, t: Throwable) {
                Log.d("server", t.message.toString())
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setNoticeData(response: List<NoticeData>) {
        noticeList.clear()
        for (notice in response) {
            noticeList.add(NoticeData(notice.id, notice.major, notice.clubName, notice.deadline, notice.applyOrNot))
        }
    }
}