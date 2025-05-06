package com.thoughtworks.carapp.ui.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.components.AirFlowControlPanel
import com.thoughtworks.carapp.ui.main.presentation.AcBoxState
import com.thoughtworks.carapp.ui.main.presentation.AirFlowState

@Composable
fun SettingScreen(
    acBoxState: AcBoxState,
    airFlowState: AirFlowState,
    toggleFrontWindowDefog: () -> Unit,
    toggleRearWindowDefog: () -> Unit,
    toggleMirrorHeat: () -> Unit,
    toggleInternalCirculation: () -> Unit
) {
    var switchOn by remember { mutableStateOf(false) }
    var acOn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .background(Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(top = 96.dp, start = 72.dp),
            ) {
                // 左侧菜单栏
                LeftControlBar(
                    switchOn = switchOn,
                    acOn = acOn,
                    switchClicked = {
                        switchOn = !switchOn
                    },
                    acClicked = {
                        if (switchOn) {
                            acOn = !acOn
                        }
                    }
                )
                // 中央内容
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TemperatureAndFanControl(acBoxState, switchOn, acOn)
                    Spacer(modifier = Modifier.height(42.dp))
                    AirFlowModePanel(
                        airFlowState,
                        toggleFrontWindowDefog,
                        toggleRearWindowDefog,
                        toggleMirrorHeat,
                        toggleInternalCirculation
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }

            Box(
                modifier = Modifier.weight(0.4f)
            ) {
                // 右侧内容
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 底部内容
            Row {

            }
        }
    }
}

@Composable
fun TemperatureAndFanControl(acBoxState: AcBoxState, switchOn: Boolean, acOn: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        TemperatureKnob(value = acBoxState.driverTemperature, switchOn, acOn)
        Column {
            Image(
                painter = painterResource(R.drawable.ic_air_vent_top),
                modifier = Modifier.wrapContentSize(),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(26.dp))
            Image(
                painter = painterResource(R.drawable.ic_air_vent_bottom),
                modifier = Modifier.wrapContentSize(),
                contentDescription = ""
            )
        }
        TemperatureKnob(value = acBoxState.coPilotTemperature, switchOn = switchOn, acOn = acOn)
    }
}

@Composable
fun TemperatureKnob(value: Float, switchOn: Boolean, acOn: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(216.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_external_circle),
                contentDescription = "",
                modifier = Modifier.size(216.dp),
            )
            Image(
                painter = painterResource(id = R.drawable.ic_internal_circle),
                contentDescription = "",
                modifier = Modifier.size(194.dp),
            )
            Image(
                painter = painterResource(id = R.drawable.ic_internal_circle),
                contentDescription = "",
                modifier = Modifier.size(108.dp),
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = if (switchOn && acOn) value.toString() else "-",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF1F3F5)
                )
                Text(
                    text = if (switchOn && acOn) "℃" else "",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFF1F3F5),
                    style = TextStyle(baselineShift = BaselineShift(0.1f)) // 尝试调整负值
                )
            }
        }
    }
}

@Composable
fun AirFlowModePanel(
    airFlowState: AirFlowState,
    toggleFrontWindowDefog: () -> Unit,
    toggleRearWindowDefog: () -> Unit,
    toggleMirrorHeat: () -> Unit,
    toggleInternalCirculation: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_wind_speed),
            modifier = Modifier.size(106.dp).padding(top = 20.dp),
            contentDescription = "风速"
        )
        AirFlowControlPanel(
            airFlowState,
            toggleFrontWindowDefog,
            toggleRearWindowDefog,
            toggleMirrorHeat,
            toggleInternalCirculation
        )
        Image(
            painter = painterResource(R.drawable.ic_wind_speed),
            modifier = Modifier.size(106.dp).padding(top = 20.dp),
            contentDescription = "风速"
        )
    }
}


@Composable
fun LeftControlBar(switchOn: Boolean, acOn: Boolean, switchClicked: () -> Unit, acClicked: () -> Unit) {
    var autoOn by remember { mutableStateOf(false) }
    var fragranceOn by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomIconButton(
            label = "switch",
            state = switchOn,
            activatedIconResId = R.drawable.ic_switch_activated,
            closedIconResId = R.drawable.ic_switch_closed
        ) {
            switchClicked()
        }
        CustomIconButton(
            label = "A/C",
            state = switchOn && acOn,
            activatedIconResId = R.drawable.ic_ac_activated,
            closedIconResId = R.drawable.ic_ac_closed
        ) {
            acClicked()
        }
        CustomIconButton(
            label = "Auto",
            state = switchOn && autoOn,
            activatedIconResId = R.drawable.ic_auto_activated,
            closedIconResId = R.drawable.ic_auto_closed
        ) {
            if (switchOn) {
                autoOn = !autoOn
            }
        }
        CustomIconButton(
            label = "香氛",
            state = switchOn && fragranceOn,
            activatedIconResId = R.drawable.ic_fragrance_activated,
            closedIconResId = R.drawable.ic_fragrance_closed
        ) {
            if (switchOn) {
                fragranceOn = !fragranceOn
            }
        }
    }
}

@Composable
fun CustomIconButton(
    label: String,
    state: Boolean,
    activatedIconResId: Int,
    closedIconResId: Int,
    onClick: () -> Unit = {}
) {
    Image(
        painter = painterResource(if (state) activatedIconResId else closedIconResId),
        contentDescription = label,
        modifier = Modifier
            .size(80.dp)
            .clickable { onClick() },
    )
}

@Preview(widthDp = 1408, heightDp = 792)
@Composable
fun PreviewSettingScreen() {
    SettingScreen(
        acBoxState = AcBoxState(),
        airFlowState = AirFlowState(),
        toggleFrontWindowDefog = {},
        toggleRearWindowDefog = {},
        toggleMirrorHeat = {},
        toggleInternalCirculation = {}
    )
}
