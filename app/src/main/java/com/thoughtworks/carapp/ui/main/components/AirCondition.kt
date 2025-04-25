package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.blindhmi.ui.component.container.stepper.Stepper.Companion.CLOCKWISE
import com.thoughtworks.blindhmi.ui.composable.border
import com.thoughtworks.blindhmi.ui.composable.center
import com.thoughtworks.blindhmi.ui.composable.indicator
import com.thoughtworks.blindhmi.ui.composable.stepper.ComposeBlindHMILoopStepper
import com.thoughtworks.blindhmi.ui.utils.moveDown
import com.thoughtworks.blindhmi.ui.utils.moveUp
import com.thoughtworks.carapp.R

@Composable
fun AirCondition(label: String, currentValue: Float, handleSweepStep: (Float) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val isTemperatureDockVisible = remember { mutableStateOf(false) }
        var text by remember { mutableFloatStateOf(currentValue) }

        LaunchedEffect(currentValue) {
            text = currentValue
        }

        ComposeBlindHMILoopStepper(
            modifier = Modifier.size(100.dp),
            centerHotspotRadius = 20.dp,
            centerBackgroundRadius = 50.dp,
            centerBackgroundRes = R.drawable.ic_dock_temperature_bg,
            stepOrientation = CLOCKWISE,
            steps = 12,
            currentValue = currentValue,
            minValue = 14f,
            maxValue = 32f,
            stepValue = 0.5f,
            startAngle = 0f,
            border = {
                border(context) {
                    imageRes = R.drawable.ic_dock_temperature_scale
                    expandRadius = context.resources.getDimensionPixelSize(R.dimen.dimen_72)
                    radius = 30
                    drawOrder = getDrawOrder() - 1
                }
            },
            indicator = {
                indicator(context) {
                    imageRes = R.drawable.ic_pointer_168
                    drawOrder = getDrawOrder() + 1
                    radius = 54
                    visible = true
                    onActive = {
                        isTemperatureDockVisible.value = true
                    }
                    onInActive = {
                        isTemperatureDockVisible.value = false
                    }
                }
            },
            center = {
                center(context) {
                    contentFactory = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(120.dp),
                        ) {
                            Image(
                                modifier = Modifier.size(100.dp),
                                painter = painterResource(id = R.drawable.ic_dock_temperature_center),
                                contentDescription = "",
                            )

                            Text(
                                text = text.toString(),
                                color = colorResource(id = R.color.gl_dark_gray_4),
                                fontSize = 20.sp,
                            )

                            if (isTemperatureDockVisible.value) {
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.ic_dock_temperature_cool_off,
                                    ),
                                    contentDescription = "",
                                )

                                Image(
                                    painter = painterResource(R.drawable.ic_dock_temperature_warm_off),
                                    contentDescription = "",
                                )
                            }
                        }
                    }
                }
            },
            onActive = { moveDown() },
            onInactive = {
                moveUp()
                handleSweepStep(text)
            },
            onSweepStep = { _, stepperValue ->
                text = stepperValue
            },
        )
        Text(label, color = Color.White)
    }
}
