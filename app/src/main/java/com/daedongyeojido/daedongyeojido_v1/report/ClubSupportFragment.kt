package com.daedongyeojido.daedongyeojido_v1.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daedongyeojido.daedongyeojido_v1.R

class ClubSupportFragment(val clubName: String, val major: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_club_support, container, false)
    }
}