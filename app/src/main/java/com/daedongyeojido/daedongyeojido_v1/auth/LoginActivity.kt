package com.daedongyeojido.daedongyeojido_v1.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.daedongyeojido.daedongyeojido_v1.ApiProvider
import com.daedongyeojido.daedongyeojido_v1.MainActivity
import com.daedongyeojido.daedongyeojido_v1.R
import com.daedongyeojido.daedongyeojido_v1.ServerApi
import com.daedongyeojido.daedongyeojido_v1.Token
import com.daedongyeojido.daedongyeojido_v1.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var id = ""
    private var pw = ""
    private var editCheck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editLoginId.addTextChangedListener(textWatcher)
        binding.editLoginPw.addTextChangedListener(textWatcher)

        binding.btnLogin.setOnClickListener {
//            if (binding.btnLogin.isEnabled)
//                login()
            if (editCheck) {
                login()
            }
        }
        updateButtonState()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            id = binding.editLoginId.text.toString()
            pw = binding.editLoginPw.text.toString()
            updateButtonState()
        }
    }

    private fun updateButtonState() {
        Log.d("button", "버튼 상태 업로드 중")
//        val isEnabled = id.isNotEmpty() && pw.isNotEmpty()
//        binding.btnLogin.isEnabled = isEnabled
//
//        val backgroundResource = if (isEnabled) R.drawable.btn_after else R.drawable.btn_before
//        binding.btnLogin.setBackgroundResource(backgroundResource)
//        Log.d("button", "backgroundResource : $backgroundResource")
        if (id.isNotEmpty() && pw.isNotEmpty()) {
            binding.btnLogin.setBackgroundResource(R.drawable.btn_after)
            editCheck = true
        } else {
            binding.btnLogin.setBackgroundResource(R.drawable.btn_before)
            editCheck = false
        }
        Log.d("button", editCheck.toString())
    }

    private fun login() {
        val apiProvider = ApiProvider.getInstance().create(ServerApi::class.java)
        apiProvider.login(LoginRequest(id, pw)).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Token().setAccessToken(responseBody.accessToken)
                        Token().setPart(responseBody.part)
                    }

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.d("server", response.code().toString())
                    Toast.makeText(this@LoginActivity, "아이디나 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("server", t.message.toString())
                Toast.makeText(this@LoginActivity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}