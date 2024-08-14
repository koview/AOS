package com.example.koview.presentation.ui.main

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.koview.R
import com.example.koview.databinding.ActivityMainBinding
import com.example.koview.presentation.base.BaseActivity
import com.example.koview.presentation.ui.main.mypage.MypageEvent
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBnv()
        initEventObserve()
    }

    private fun setBnv() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.mainBnv.apply {
            setupWithNavController(navController)
            setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, navController)
                navController.popBackStack(item.itemId, inclusive = false)
                true
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.home_fragment || destination.id == R.id.coview_fragment
                || destination.id == R.id.ask_fragment || destination.id == R.id.mypage_fragment
            ) {
                binding.mainBnv.visibility = View.VISIBLE
            } else {
                binding.mainBnv.visibility = View.INVISIBLE
            }
        }
    }

    private val TAG = "GetGallery"

    // 권한 요청
    private val galleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // 다중 선택 허용
                }
                imageLauncher.launch(intent)
            } else {
                Log.d(TAG, "deny")
                showToastMessage("권한을 거부했습니다.")
            }
        }

    // 갤러리에서 이미지 가져오기
    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(TAG, "imageLauncher enter!!")
            if (result.resultCode == RESULT_OK) {
                // 선택된 이미지가 여러 개일 수 있으므로 처리
                result.data?.let { data ->
                    if (data.clipData != null) {
                        val count = data.clipData!!.itemCount
                        for (i in 0 until count) {
                            val imageUri = data.clipData!!.getItemAt(i).uri
                            viewModel.imageList.value = viewModel.imageList.value + (imageUri.toString()) // URI를 문자열로 변환하여 리스트에 추가
                        }
                    } else {
                        // 단일 이미지 선택
                        val imageUri = data.data
                        imageUri?.let {
                            viewModel.imageList.value = viewModel.imageList.value + (it.toString()) // URI를 문자열로 변환하여 리스트에 추가
                        }
                    }
                }
                Log.d(TAG, "Image List: ${viewModel.imageList}") // 선택된 이미지 리스트 로그
            }
        }

    private fun getImageToGallery(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        else
            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is MainEvent.GetGallery -> getImageToGallery()
                    else -> {}
                }
            }
        }
    }
}