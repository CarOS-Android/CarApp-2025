package com.thoughtworks.carapp.ui.main.components

import android.util.Log
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toggle
import com.thoughtworks.carapp.ui.main.viewmodel.CarViewModel

@Composable
fun CarLight(viewModel: CarViewModel) {
    val highBeamState by viewModel.highBeamState.collectAsState()
    val hazardLightState by viewModel.hazardLightsState.collectAsState()
    val headLightsState by viewModel.headLightsState.collectAsState()
    Image(
        painter = when (hazardLightState) {
            Toggle.Off -> painterResource(id = R.drawable.light1_off)
            Toggle.On -> painterResource(id = R.drawable.light1_on)
        },
        modifier = Modifier
            .width(85.dp)
            .clickable {
                Log.d("huoxiaolu", "begin to change the switch")
                viewModel.toggleHazardLights()
                       },
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(32.dp))
    Image(
        painter = when (headLightsState) {
            Toggle.Off -> painterResource(id = R.drawable.light2_off)
            Toggle.On -> painterResource(id = R.drawable.light2_on)
        },
        modifier = Modifier
            .width(85.dp)
            .clickable { viewModel.toggleHeadLights() },
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(32.dp))
    Image(
        painter = when (highBeamState) {
            Toggle.Off -> painterResource(id = R.drawable.light3_off)
            Toggle.On -> painterResource(id = R.drawable.light3_on)
        },
        modifier = Modifier
            .width(85.dp)
            .clickable { viewModel.toggleHighBeamLights() },
        contentDescription = null
    )

    Button(
        onClick = {
            Log.d("huoxiaolu", "测试button点击事件")
            viewModel.toggleHazardLights()
        }
    ) {
        Text("你好！！！！", color = Color.Red)
    }
}