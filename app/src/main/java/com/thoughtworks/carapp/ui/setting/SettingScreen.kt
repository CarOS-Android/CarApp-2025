package com.thoughtworks.carapp.ui.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.setting.components.AreaSeat
import com.thoughtworks.carapp.ui.setting.components.SeatControl
import com.thoughtworks.carapp.ui.setting.presentation.SeatControlUiState
import com.thoughtworks.carapp.ui.setting.presentation.SeatEvent
import com.thoughtworks.carapp.ui.setting.viewmodel.SeatViewModel

@Composable
fun SettingScreen() {
    val seatViewModel: SeatViewModel = hiltViewModel()
    val seatOperationState = seatViewModel.operationState.collectAsState()

    SeatControlRow(seatOperationState.value, seatViewModel::handleEvent)
}

@Composable
fun SeatControlRow(seatState: SeatControlUiState, handleEvent: (SeatEvent) -> Unit) {
    Row(
        modifier = Modifier
            .height(207.dp)
            .fillMaxWidth()
            .padding(start = 53.dp, end = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        AreaSeat.entries.forEach { seat ->
            Box(modifier = Modifier.weight(1f)) {
                SeatControl(
                    areaSeat = seat,
                    seatState = seatState.getSeatState(seat),
                    handleEvent = handleEvent
                )
            }
        }
        Image(
            modifier = Modifier.height(207.dp),
            painter = painterResource(id = R.drawable.ambient_light),
            contentDescription = ""
        )
    }
}

@Preview(widthDp = 1408, heightDp = 792)
@Composable
fun ScreenPreview() {
    SeatControlRow(seatState = SeatControlUiState(), handleEvent = {})
}
