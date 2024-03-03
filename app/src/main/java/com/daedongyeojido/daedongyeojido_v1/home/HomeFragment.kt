package com.daedongyeojido.daedongyeojido_v1.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.daedongyeojido.daedongyeojido_v1.ApiProvider
import com.daedongyeojido.daedongyeojido_v1.MainActivity
import com.daedongyeojido.daedongyeojido_v1.ServerApi
import com.daedongyeojido.daedongyeojido_v1.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), HomeClubAdapter.ClubClickListener {
    private lateinit var binding: FragmentHomeBinding

    private val bannerList = mutableListOf<BannerData>()
    private lateinit var bannerAdapter: BannerAdapter
    private var bannerPosition = Int.MAX_VALUE / 2
    private var bannerHandler = BannerHandler()
    private val intervalTime = 1500.toLong()

    private val clubList = mutableListOf<HomeClubData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        getMainData()
        bannerList.add(BannerData(0, "https://i.namu.wiki/i/8xs7J5zxBnPuPpi8PFfxmDR2WOtimMy693D3gj3PmzfxsnrL-NpTV2YOlx-Mh2WwUMnj8ohPQNJRkdQX5ptlnKZ-JnnSMmk6ofE_lKuDaKZJ6DUh7BPNLKicEipDnQUAdG1hJrjkE6i9fMmdpVcLYA.webp"))
        bannerList.add(BannerData(1, "https://i.namu.wiki/i/QKN-HoWEdgXb_im28JnL-k8s66aY2OxFwuGJvmDbUAH9betADbwfDXRc6EXoGdHl9FqiZnB9wUatNx8UhtqqgoCIFqy1nDc6Nq6XMZB7gXN4HG7n0HR2RYkaWcWepDuPow8Gat7KqLSug3GFD0IBrw.webp"))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("banner", bannerList.toString())
        // val bannerAdapter = BannerAdapter(bannerList)

        // bannerAdapter = BannerAdapter(bannerList)
        // setBanner(bannerAdapter)

        val clubAdapter = HomeClubAdapter(clubList, this)
        setClubAdapter(clubAdapter)
    }

    private fun getMainData() {
        val apiProvider = ApiProvider.getInstance().create(ServerApi::class.java)
        apiProvider.getMain().enqueue(object : Callback<HomeClubResponse> {
            override fun onResponse(call: Call<HomeClubResponse>, response: Response<HomeClubResponse>) {
                if (response.isSuccessful) {
                    val homeClubResponse = response.body()?.allClubResponses
                    // val homeAnnouncementResponse = response.body()?.announcement
                    val homeBannerResponse = response.body()?.banners
                    Log.d("main", response.body().toString())

                    homeClubResponse?.let { setClubList(it) }
                    // homeAnnouncementResponse?.let { setAnnouncement(it) }
                    homeBannerResponse?.let { setBannerList(it) }
                } else {
                    Log.d("server", response.code().toString())
                }
            }

            override fun onFailure(call: Call<HomeClubResponse>, t: Throwable) {
                Log.d("server", t.message.toString())
                Toast.makeText(activity, "서버와의 통신에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setClubList(response: List<HomeClubData>) {
        Log.d("setList", response.toString())
        clubList.clear()
        for(club in response) {
            val clubName = club.clubName
            val clubImgUrl = club.clubImageUrl
            val title = club.title
            val tag = club.tags
            clubList.add(HomeClubData(clubName, title, clubImgUrl, tag))
        }
    }
    private fun setBannerList(response: List<BannerData>) {
        Log.d("setList", response.toString())
        for(banner in response) {
            bannerList.add(banner)
        }
        bannerAdapter = BannerAdapter(bannerList)
        // bannerAdapter.notifyDataSetChanged()
        setBanner(bannerAdapter)
    }
//    private fun setAnnouncement(response: List<HomeAnnouncementData>) {
//        binding.textHomeNotice.text = response[0].title
//    }
    private fun setClubAdapter(clubAdapter: HomeClubAdapter) {
        binding.recyclerHome.adapter = clubAdapter
        binding.recyclerHome.layoutManager = LinearLayoutManager(activity)
    }

    private fun setBanner(bannerAdapter: BannerAdapter) {
        binding.viewHomeBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewHomeBanner.adapter = bannerAdapter
        binding.viewHomeBanner.setCurrentItem(bannerPosition, false)

        binding.viewHomeBanner.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when(state) {
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                    }
                }
            })
        }
    }
    inner class BannerHandler: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                binding.viewHomeBanner.setCurrentItem(++bannerPosition, true)
                autoScrollStart(intervalTime)
            }
        }
    }
    private fun autoScrollStart(intervalTime: Long) {
        bannerHandler.removeMessages(0)
        bannerHandler.sendEmptyMessageDelayed(0, intervalTime)
    }
    private fun autoScrollStop() {
        bannerHandler.removeMessages(0)
    }
    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }
    override fun onDestroy() {
        super.onDestroy()
        autoScrollStop()
    }

    override fun onClubClicked(clubName: String) {
        (activity as MainActivity).clubDetail(clubName)
    }
}