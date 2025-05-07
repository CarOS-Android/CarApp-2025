package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.thoughtworks.carapp.ui.main.Toggle
import com.thoughtworks.carapp.ui.main.presentation.AirFlowState

@Composable
fun AirFlowControlPanel(
    airFlowState: AirFlowState,
    toggleFrontWindowDefog: () -> Unit,
    toggleRearWindowDefog: () -> Unit,
    toggleMirrorHeat: () -> Unit,
    toggleInternalCirculation: () -> Unit,
) {
    Box(
        modifier = Modifier.wrapContentSize(),
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
                    modifier = Modifier.clickable { toggleFrontWindowDefog() },
                    painter = when (airFlowState.frontWindowDefogState) {
                        Toggle.Off -> painterResource(id = R.drawable.ic_ac_front_window_defog_off)
                        Toggle.On -> painterResource(id = R.drawable.ic_ac_front_window_defog_on)
                    },
                    contentDescription = "air flow control panel",
                )
                Image(
                    modifier = Modifier.clickable { toggleRearWindowDefog() },
                    painter = when (airFlowState.rearWindowDefogState) {
                        Toggle.Off -> painterResource(id = R.drawable.ic_ac_rear_window_defog_off)
                        Toggle.On -> painterResource(id = R.drawable.ic_ac_rear_window_defog_on)
                    },
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
                    modifier = Modifier.clickable {  toggleMirrorHeat() },
                    painter = when (airFlowState.mirrorHeatState) {
                        Toggle.Off -> painterResource(id = R.drawable.ic_ac_mirror_heat_off)
                        Toggle.On -> painterResource(id = R.drawable.ic_ac_mirror_heat_on)
                    },
                    contentDescription = "air flow control panel",
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Image(
                    modifier = Modifier.clickable { toggleInternalCirculation() },
                    painter = when (airFlowState.internalCirculationState) {
                        Toggle.Off -> painterResource(id = R.drawable.ic_ac_external_circulation_on)
                        Toggle.On -> painterResource(id = R.drawable.ic_ac_external_circulation_off)
                    },
                    contentDescription = "air flow control panel",
                )
                Image(
                    modifier = Modifier.clickable { toggleInternalCirculation() },
                    painter = when (airFlowState.internalCirculationState) {
                        Toggle.Off -> painterResource(id = R.drawable.ic_ac_internal_circulation_off)
                        Toggle.On -> painterResource(id = R.drawable.ic_ac_internal_circulation_on)
                    },
                    contentDescription = "air flow control panel",
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAirFlowControlPanel() {
    AirFlowControlPanel(
        AirFlowState(
            frontWindowDefogState = Toggle.On,
            rearWindowDefogState = Toggle.Off,
            mirrorHeatState = Toggle.Off,
            internalCirculationState = Toggle.Off,
            fanState = 1,
        ), {}, {}, {}, {})
}