package com.daedongyeojido.daedongyeojido_v1

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.daedongyeojido.daedongyeojido_v1.databinding.CustomDialogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomDialog(private val type: String, private val id: Long, private val title: String, private val content: String, private val accept: String, private val cancel: String) : DialogFragment() {
    private lateinit var binding: CustomDialogBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CustomDialogBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.textDialogTitle.text = title
        binding.textDialogContent.text = content
        binding.textDialogOk.text = accept
        binding.textDialogCancel.text = cancel

        binding.textDialogCancel.setOnClickListener {
            if (type == "Dinner") {
                cancelClubMess()
            }
            dismiss()
        }
        binding.textDialogOk.setOnClickListener {
            if (type == "Notice") {
                acceptNoticeCancel()
            } else {
                acceptClubMess()
            }
            dismiss()
        }
        return binding.root
    }

    private fun acceptNoticeCancel() {
        val apiProvider = ApiProvider.getInstance().create(ServerApi::class.java)
        apiProvider.deleteApply(id, Token().getAccessToken()).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, "지원이 취소되었습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("server", response.code().toString())
                    Log.d("server", response.message().toString())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("server", t.message.toString())
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun acceptClubMess() {

    }
    private fun cancelClubMess() {
        val apiProvider = ApiProvider.getInstance().create(ServerApi::class.java)
        apiProvider
    }
}