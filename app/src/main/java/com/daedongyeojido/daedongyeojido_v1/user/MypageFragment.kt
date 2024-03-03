package com.daedongyeojido.daedongyeojido_v1.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.daedongyeojido.daedongyeojido_v1.ApiProvider
import com.daedongyeojido.daedongyeojido_v1.R
import com.daedongyeojido.daedongyeojido_v1.ServerApi
import com.daedongyeojido.daedongyeojido_v1.Token
import com.daedongyeojido.daedongyeojido_v1.databinding.FragmentMypageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private val reportList = mutableListOf<ReportData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMypageBinding.inflate(layoutInflater, container, false)
        getMyData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MypageAdapter(reportList)
        binding.recyclerMy.adapter = adapter
        binding.recyclerMy.layoutManager = LinearLayoutManager(activity)
    }

    private fun getMyData() {
        val apiProvider = ApiProvider.getInstance().create(ServerApi::class.java)
        apiProvider.getMyInfo(Token().getAccessToken()).enqueue(object : Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                if (response.isSuccessful) {
                    val userData = response.body()
                    val reportData = response.body()?.myReport
                    userData?.let { setUserData(it) }
                    reportData?.let { setReport(it) }
                } else {
                    Log.d("server", response.code().toString())
                }
            }

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                Log.d("server", t.message.toString())
            }
        })
    }

    private fun setUserData(response: MypageResponse) {
        binding.textMyName.text = response.name
        binding.textMyNumber.text = response.classNumber
        binding.textMyClub.text = response.myClub
    }
    private fun setReport(reportResponse: List<ReportData>) {
        for (i in reportResponse) {
            reportList.add(i)
        }
    }
}