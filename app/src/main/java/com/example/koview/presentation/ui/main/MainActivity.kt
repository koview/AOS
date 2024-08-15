package com.example.koview.presentation.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.koview.R
import com.example.koview.databinding.ActivityMainBinding
import com.example.koview.presentation.base.BaseActivity
import com.example.koview.presentation.ui.main.mypage.MypageEvent
import com.example.koview.presentation.utils.Constants.STORAGE_PERMISSION_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    private val storagePermissionList =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                // 안드로이드 13 이상 필요한 권한들
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                // 안드로이드 13 미만 필요한 권한들
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    private lateinit var neededPermissionList: MutableList<String>

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
    private fun initEventObserve() {
        repeatOnStarted {
            viewModel.event.collect {
                when (it) {
                    is MainEvent.GoToGetImage -> onCheckImagePermissions()
                    is MainEvent.ShowToastMessage -> showToastMessage(it.msg)
                }
            }
        }
    }
    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val clipData = result.data?.clipData // 여러 장의 이미지
                val uriList = mutableListOf<Uri>()

                // 다중 선택된 경우
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        uriList.add(clipData.getItemAt(i).uri)
                    }
                } else {
                    // 단일 선택된 경우
                    result.data?.data?.let { uri ->
                        uriList.add(uri)
                    }
                }

                if (uriList.isNotEmpty()) {
                    viewModel.setImageUri(uriList)
                }
            }
        }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_IMAGE -> {
                neededPermissionList.forEach {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            it
                        ) != PackageManager.PERMISSION_GRANTED
                    ) return
                }
                openGalleryForImage()
            }
        }
    }
    // 사진 권한 확인
    private fun onCheckImagePermissions() {
        neededPermissionList = mutableListOf()
        storagePermissionList.forEach { permission ->
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) neededPermissionList.add(permission)
        }
        if (neededPermissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                neededPermissionList.toTypedArray(),
                STORAGE_PERMISSION_IMAGE
            )
        } else {
            openGalleryForImage()
        }
    }
    // 이미지 가져오기
    private fun openGalleryForImage() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // 다중 선택 가능하게 설정
            }
        imageLauncher.launch(galleryIntent)
    }
}