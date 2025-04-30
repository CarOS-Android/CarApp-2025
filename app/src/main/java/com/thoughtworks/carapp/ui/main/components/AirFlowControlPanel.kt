package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun AirFlowControlPanel() {
    Box(
        modifier = Modifier
            .wrapContentSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_ac_fill),
            contentDescription = "air flow control panel",
        )
        Image(
            painter = painterResource(R.drawable.ic_ac_panel_circle),
            contentDescription = "air flow control panel",
        )

        Column(
            modifier = Modifier.matchParentSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_ac_front_window_defog_off),
                    contentDescription = "air flow control panel",
                )
                Image(
                    painter = painterResource(R.drawable.ic_ac_rear_window_defog_off),
                    contentDescription = "air flow control panel",
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_ac_mirror_heat_off),
                    contentDescription = "air flow control panel",
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_ac_external_circulation_off),
                    contentDescription = "air flow control panel",
                )
                Image(
                    painter = painterResource(R.drawable.ic_ac_internal_circulation_off),
                    contentDescription = "air flow control panel",
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAirFlowControlPanel() {
    AirFlowControlPanel()
}