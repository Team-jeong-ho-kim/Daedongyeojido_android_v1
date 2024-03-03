package com.daedongyeojido.daedongyeojido_v1.club.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.daedongyeojido.daedongyeojido_v1.R
import com.daedongyeojido.daedongyeojido_v1.club.ClubInfo
import com.daedongyeojido.daedongyeojido_v1.databinding.FragmentClubInfoBinding

class ClubInfoFragment(private val clubInfo: ClubInfo) : Fragment() {
    private lateinit var binding: FragmentClubInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClubInfoBinding.inflate(layoutInflater, container, false)
        setClubInfo()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardClubInfoSecond.setOnClickListener {
            clickToggle(binding.textClubInfoSecondMember, binding.imgClubInfoDropdownSecond)
        }
        binding.cardClubInfoThird.setOnClickListener {
            clickToggle(binding.textClubInfoThirdMember, binding.imgClubInfoDropdownThird)
        }
    }

    private fun setClubInfo() {
        binding.textClubInfoIntroduceContent.text = clubInfo.introduction
        binding.textClubInfoProjectContent.text = clubInfo.project
        binding.textClubInfoWantContent.text = clubInfo.weWant

        binding.textClubInfoSecondMember.text = clubInfo.clubMembers[0].name
        binding.textClubInfoThirdMember.text = clubInfo.clubMembers[1].name

        for (tag in clubInfo.tags) {
            val tagView = TextView(binding.root.context)
            tagView.text = tag
            tagView.setTextAppearance(R.style.text_club_tag)
            binding.layClubInfoTag.addView(tagView)
        }
    }

    private fun clickToggle(member: TextView, dropdown: ImageView) {
        if (member.visibility == View.VISIBLE) {
            member.visibility = View.GONE
            dropdown.animate().apply {
                duration = 300
                rotation(90f)
            }
        } else {
            member.visibility = View.VISIBLE
            dropdown.animate().apply {
                duration = 300
                rotation(0f)
            }
        }
    }
}