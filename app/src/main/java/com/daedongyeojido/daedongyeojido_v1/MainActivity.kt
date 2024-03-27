package com.daedongyeojido.daedongyeojido_v1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.daedongyeojido.daedongyeojido_v1.club.ClubDetailFragment
import com.daedongyeojido.daedongyeojido_v1.club.ClubInfo
import com.daedongyeojido.daedongyeojido_v1.club.info.ClubInfoFragment
import com.daedongyeojido.daedongyeojido_v1.club.notice.ClubNoticeFragment
import com.daedongyeojido.daedongyeojido_v1.club.qna.ClubQnaFragment
import com.daedongyeojido.daedongyeojido_v1.club.QnAData
import com.daedongyeojido.daedongyeojido_v1.databinding.ActivityMainBinding
import com.daedongyeojido.daedongyeojido_v1.home.HomeFragment
import com.daedongyeojido.daedongyeojido_v1.report.ClubSupportFragment
import com.daedongyeojido.daedongyeojido_v1.user.MypageFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setDrawerMenu()
        setAppBar()
        changeFragmentDrawer(0)

        binding.navigationMain.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_mypage -> changeFragmentDrawer(1)
            R.id.menu_notice -> changeFragmentDrawer(0)
            R.id.menu_application -> changeFragmentDrawer(0)
            R.id.menu_management -> changeFragmentDrawer(0)
            R.id.menu_request -> changeFragmentDrawer(0)
            R.id.menu_accept -> changeFragmentDrawer(0)
        }
        binding.layMainDrawer.closeDrawers()
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> binding.layMainDrawer.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeFragmentDrawer(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        when(index) {
            0 -> transaction.replace(R.id.lay_main_frame, HomeFragment())
            1 -> transaction.replace(R.id.lay_main_frame, MypageFragment())
        }
        transaction.commit()
    }

    private fun setDrawerMenu() {
        val sidebar = binding.navigationMain
        when(Token().getPart()) {
           // "INDEPENDENT" ->
        }
    }
    private fun setAppBar() {
        setSupportActionBar(findViewById(R.id.lay_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_menu)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

//    fun changeFragment(index: Int) {
//        val fragmentManager = supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        when(index) {
//            0 -> transaction.replace(R.id.lay_main_frame, ClubSupportFragment())
//        }
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }

    fun clubDetail(name: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.lay_main_frame, ClubDetailFragment(name))
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun clubDetailTab(index: Int, qna: List<QnAData>, clubInfo: ClubInfo, clubName: String) {
        val transaction = supportFragmentManager.beginTransaction()
        when(index) {
            0 -> transaction.replace(R.id.lay_detail_frame, ClubInfoFragment(clubInfo))
            1 -> transaction.replace(R.id.lay_detail_frame, ClubNoticeFragment(clubName))
            2 -> transaction.replace(R.id.lay_detail_frame, ClubQnaFragment(qna))
        }
        transaction.commit()
    }

    fun clubSupportChange(clubName: String, major: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.lay_main_frame, ClubSupportFragment(clubName, major))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun setMajor(major: String): String {
        when(major) {
            "FRONT" -> return "프론트엔드"
            "BACK" -> return "백엔드"
            "IOS" -> return "iOS"
            "AND" -> return "안드로이드"
            "FLUTTER" -> return "플러터"
            "EMBEDDED" -> return "임베디드"
            "AI" -> return "인공지능"
            "SECURITY" -> return "보안"
            "DEVOPS" -> return "데브옵스"
            "DESIGN" -> return "디자인"
            "GAME" -> return "게임 개발"
        }
        return ""
    }
}