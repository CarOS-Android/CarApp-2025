package com.thoughtworks.carapp.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thoughtworks.carapp.ui.setting.components.AreaSeat
import com.thoughtworks.carapp.ui.setting.components.SeatControl

@Preview(widthDp = 1400, heightDp = 600)
@Composable
fun SettingScreen() {
    Row {
        Column {
            SeatControl(AreaSeat.DRIVER)
        }
        Column {
            SeatControl(AreaSeat.PASSENGER)
        }
    }
}