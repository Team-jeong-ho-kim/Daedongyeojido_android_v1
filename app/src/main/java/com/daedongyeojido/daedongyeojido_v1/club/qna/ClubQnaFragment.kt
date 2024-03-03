package com.daedongyeojido.daedongyeojido_v1.club.qna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daedongyeojido.daedongyeojido_v1.club.QnAData
import com.daedongyeojido.daedongyeojido_v1.databinding.FragmentClubQnaBinding

class ClubQnaFragment(private val qna: QnAData) : Fragment() {
    private lateinit var binding: FragmentClubQnaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClubQnaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}