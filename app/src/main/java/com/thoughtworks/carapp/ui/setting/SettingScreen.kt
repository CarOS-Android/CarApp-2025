package com.thoughtworks.carapp.ui.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.components.AirFlowControlPanel
import com.thoughtworks.carapp.ui.main.presentation.AirFlowState

@Composable
fun SettingScreen(
    airFlowState: AirFlowState,
    toggleFrontWindowDefog: () -> Unit,
    toggleRearWindowDefog: () -> Unit,
    toggleMirrorHeat: () -> Unit,
    toggleExternalCirculation: () -> Unit,
    toggleInternalCirculation: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // 左侧菜单栏
        LeftControlBar()

        Spacer(modifier = Modifier.width(16.dp))

        // 中央内容
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TemperatureAndFanControl()
            Spacer(modifier = Modifier.height(24.dp))
            AirFlowModePanel(
                airFlowState,
                toggleFrontWindowDefog,
                toggleRearWindowDefog,
                toggleMirrorHeat,
                toggleExternalCirculation,
                toggleInternalCirculation
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(widthDp = 1408, heightDp = 792)
@Composable
fun PreviewSettingScreen() {
    SettingScreen(
        airFlowState = AirFlowState(),
        toggleFrontWindowDefog = {},
        toggleRearWindowDefog = {},
        toggleMirrorHeat = {},
        toggleExternalCirculation = {},
        toggleInternalCirculation = {}
    )
}

@Composable
fun TemperatureAndFanControl() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        TemperatureKnob(value = 28)
//        FanLevel(level = 3)
        TemperatureKnob(value = 25)
    }
}

@Composable
fun TemperatureKnob(value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_dock_temperature_bg),
                contentDescription = "电源按钮",
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
fun AirFlowModePanel(
    airFlowState: AirFlowState,
    toggleFrontWindowDefog: () -> Unit,
    toggleRearWindowDefog: () -> Unit,
    toggleMirrorHeat: () -> Unit,
    toggleExternalCirculation: () -> Unit,
    toggleInternalCirculation: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AirFlowControlPanel(
            airFlowState,
            toggleFrontWindowDefog,
            toggleRearWindowDefog,
            toggleMirrorHeat,
            toggleExternalCirculation,
            toggleInternalCirculation
        )
    }
}


@Composable
fun LeftControlBar() {
    var powerOn by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.width(64.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PowerButton(isOn = powerOn) { powerOn = !powerOn }
        CustomIconButton("A/C", R.drawable.ic_dock_media_ctrl_border)
        CustomIconButton("Auto", R.drawable.ic_dock_media_ctrl_border)
        CustomIconButton("香氛", R.drawable.ic_dock_media_ctrl_border)
    }
}

@Composable
fun PowerButton(isOn: Boolean, onToggle: () -> Unit) {
    val backgroundColor = if (isOn) Color.Blue else Color.DarkGray
    val iconColor = if (isOn) Color.White else Color.LightGray

    Box(
        modifier = Modifier
            .size(48.dp)
            .background(backgroundColor, shape = CircleShape)
            .clickable { onToggle() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dock_media_ctrl_border), // 你的自定义电源图标
            contentDescription = "电源按钮",
            modifier = Modifier.size(24.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconColor)
        )
    }
}


@Composable
fun CustomIconButton(label: String, iconResId: Int, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(64.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, color = Color.White, fontSize = 12.sp)
    }
}
