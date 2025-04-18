package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toogle

@Composable
fun Engine(state : Toogle, onStateChange: (Toogle) -> Unit) {
    Button(
        onClick = {
            onStateChange(state) // 将新的状态通过回调返回
        },
        modifier = Modifier.background(Color.Transparent),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        )
    ) {
        Image(
            painter = when (state) {
                Toogle.Off -> painterResource(id = R.drawable.engine_off)
                Toogle.On -> painterResource(id = R.drawable.engine_on)
            },
            contentDescription = null
        )
    }
}