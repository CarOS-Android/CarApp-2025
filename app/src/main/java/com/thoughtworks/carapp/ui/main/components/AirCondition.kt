package com.thoughtworks.carapp.ui.main.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
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
fun AirCondition() {
    Column(
        Modifier.border(width = 1.dp, color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "TemperatureStepperTest", color = Color.White, fontSize = 10.sp)

        val isTemperatureDockVisible = remember { mutableStateOf(false) }
        val text = remember { mutableStateOf("") }

        ComposeBlindHMILoopStepper(
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_168)),
            centerHotspotRadius = 36.dp,
            centerBackgroundRadius = dimensionResource(id = R.dimen.dimen_72),
            centerBackgroundRes = R.drawable.ic_dock_temperature_bg,
            stepOrientation = CLOCKWISE,
            steps = 4,
            currentValue = 2f,
            minValue = 0f,
            maxValue = 8f,
            stepValue = 1f,
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
                    radius = 72
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
                            modifier = Modifier.size(180.dp),
                        ) {
                            Image(
                                modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_144)),
                                painter = painterResource(id = R.drawable.ic_dock_temperature_center),
                                contentDescription = "",
                            )

                            Text(
                                text = text.value.ifEmpty { getCurrentValue().toString() },
                                color = colorResource(id = R.color.gl_dark_gray_4),
                                fontSize = 10.sp,
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
            onInactive = { moveUp() },
            onSweepStep = { _, stepperValue ->
                text.value = stepperValue.toString()
            },
            onStepHover = { step ->
                Log.i(TAG, "onStepHover: step = $step")
            },
        )
    }
}
