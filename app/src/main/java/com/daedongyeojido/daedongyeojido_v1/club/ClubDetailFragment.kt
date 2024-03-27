package com.daedongyeojido.daedongyeojido_v1.club

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.daedongyeojido.daedongyeojido_v1.ApiProvider
import com.daedongyeojido.daedongyeojido_v1.MainActivity
import com.daedongyeojido.daedongyeojido_v1.R
import com.daedongyeojido.daedongyeojido_v1.ServerApi
import com.daedongyeojido.daedongyeojido_v1.databinding.FragmentClubDetailBinding
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClubDetailFragment(private val clubName: String) : Fragment() {
    private lateinit var binding: FragmentClubDetailBinding
    private lateinit var clubInfo: ClubInfo
    private lateinit var qnaResponse: List<QnAData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClubDetailBinding.inflate(layoutInflater, container, false)
        getClubInfo()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity
        binding.layDetailTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position) {
                    0 -> mainActivity.clubDetailTab(0, qnaResponse, clubInfo, clubName)
                    1 -> mainActivity.clubDetailTab(1, qnaResponse, clubInfo, clubName)
                    2 -> mainActivity.clubDetailTab(2, qnaResponse, clubInfo, clubName)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun getClubInfo() {
        val apiProvider = ApiProvider.getInstance().create(ServerApi::class.java)
        apiProvider.getClubInfo(clubName).enqueue(object : Callback<ClubDetailResponse> {
            override fun onResponse(call: Call<ClubDetailResponse>, response: Response<ClubDetailResponse>) {
                if (response.isSuccessful) {
                    val clubResponse = response.body()
                    if (clubResponse != null) {
                        setClubInfo(clubResponse)
                    }
                } else {
                    Log.d("server", response.code().toString())
                }
            }

            override fun onFailure(call: Call<ClubDetailResponse>, t: Throwable) {
                Log.d("server", t.message.toString())
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setClubInfo(response: ClubDetailResponse) {
        binding.textDetailName.text = response.clubName
        binding.textDetailContent.text = response.title
        Glide.with(binding.imgDetailLogo.context).load(response.clubImageUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20))).into(binding.imgDetailLogo)

        clubInfo = ClubInfo(response.introduction, response.project, response.weWant, response.tags, response.clubMembers)
        qnaResponse = response.questResponses
    }
}