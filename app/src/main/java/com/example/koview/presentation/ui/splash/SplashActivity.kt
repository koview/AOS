package com.example.koview.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.koview.databinding.ActivitySplashBinding
import com.example.koview.presentation.base.BaseActivity
import com.example.koview.presentation.ui.intro.IntroActivity
import com.example.koview.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEventObserve()

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.checkLogin()
        }, 2000)
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is SplashEvent.NavigateToIntroActivity -> {
                        startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                        finish()
                    }

                    is SplashEvent.NavigateToMainActivity -> {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}