package com.thoughtworks.carapp.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.thoughtworks.carapp.ui.main.components.CarControl
import com.thoughtworks.carapp.ui.main.components.CarMedia
import com.thoughtworks.carapp.ui.main.viewmodel.CarViewModel

@Composable
fun MainScreen() {
    // 获取车辆信息Car
    val viewModel: CarViewModel = hiltViewModel() // 改为使用hiltViewModel
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
        ) {
            CarControl(viewModel)
        }
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
        ) {
            CarMedia()
        }
    }
}

enum class Toggle {
    On,
    Off;

    fun toggle(): Toggle {
        return when (this) {
            On -> Off
            Off -> On
        }
    }
}