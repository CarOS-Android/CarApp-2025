package com.thoughtworks.carapp.ui.setting

import androidx.compose.runtime.Composable
import com.thoughtworks.carapp.ui.setting.components.AreaSeat
import com.thoughtworks.carapp.ui.setting.components.SeatControl

@Composable
fun SettingScreen() {
    SeatControl(AreaSeat.DRIVER)
}