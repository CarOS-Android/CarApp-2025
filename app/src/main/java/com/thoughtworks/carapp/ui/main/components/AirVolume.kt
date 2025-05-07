package com.thoughtworks.carapp.ui.main.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thoughtworks.blindhmi.ui.component.container.slider.Slider
import com.thoughtworks.blindhmi.ui.composable.ComposeOnSlideListener
import com.thoughtworks.blindhmi.ui.composable.border
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.slider.ComposeBlindHMISlider
import com.thoughtworks.blindhmi.ui.utils.moveDown
import com.thoughtworks.blindhmi.ui.utils.moveUp
import com.thoughtworks.carapp.R

@Composable
fun AirVolume(
    currentVolumeState: Int,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    label: String,
    onAirVolumeChange: (Int) -> Unit
) {
    var internalVolumeState by remember { mutableStateOf(currentVolumeState) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(100.dp), // 根据旋钮大小调整
            contentAlignment = Alignment.Center
        )
        {
            ComposeBlindHMISlider(
                modifier = Modifier.size(84.dp),
                centerBackgroundRadius = 12.dp,
                centerBackgroundRes = R.drawable.ic_dock_temperature_bg,
                stepOrientation = Slider.COUNTERCLOCKWISE,
                startAngle = -120f,
                endAngle = 239f,
                startValue = 0f,
                endValue = 4f,
                centerHotspotRadius = 30.dp,
                border = {
                    border(context) {
                        imageRes = R.drawable.ic_dock_temperature_scale
                        expandRadius =
                            context.resources.getDimensionPixelSize(R.dimen.default_border_radius)
                        radius = context.resources.getDimensionPixelSize(R.dimen.dimen_30)
                        drawOrder = getDrawOrder() - 1
                    }
                },
                center = {
                    center(context) {
                        contentFactory = {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.size(100.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_ac_air_volume),
                                    contentDescription = null
                                )
                                // 当前风量图层覆盖上来
                                Image(
                                    painter = when (internalVolumeState) {
                                        0 -> painterResource(id = R.drawable.ic_ac_air_volume_center_0)
                                        1 -> painterResource(id = R.drawable.ic_ac_air_volume_center_1)
                                        2 -> painterResource(id = R.drawable.ic_ac_air_volume_center_2)
                                        3 -> painterResource(id = R.drawable.ic_ac_air_volume_center_3)
                                        else -> painterResource(id = R.drawable.ic_ac_air_volume_center_3)
                                    },
                                    contentDescription = null
                                )
                            }
                        }
                    }
                },
                indicator = {
                    indicator(context) {
                        imageRes = R.drawable.ic_panel_seat_temp_pointer
                        drawOrder = getDrawOrder() + 1
                        radius = context.resources.getDimensionPixelSize(R.dimen.dimen_45)
                        expandRadius = context.resources.getDimensionPixelSize(R.dimen.dimen_90)
                        visible = true
                    }
                },
                onSlideListener = ComposeOnSlideListener(
                    onValueSelectedFunc = { value ->
                        onAirVolumeChange(value.toInt())
                    },
                    onValueHoverFunc = { value ->
                        internalVolumeState = value.toInt()
                    }
                ),
                onActive = { this.moveDown() },
                onInactive = { this.moveUp() }
            )
        }
        Text(label, color = Color.White)
    }
}


@Preview
@Composable
fun PreviewAirVolume() {
    AirVolume(0, label = "风量", onAirVolumeChange = {})
}