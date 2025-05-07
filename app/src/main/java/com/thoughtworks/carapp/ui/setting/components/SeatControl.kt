package com.thoughtworks.carapp.ui.setting.components

import android.car.VehiclePropertyIds
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.setting.presentation.SeatEvent
import com.thoughtworks.carapp.ui.setting.presentation.SingleSeatState

@Composable
fun SeatControl(areaSeat: AreaSeat, seatState: SingleSeatState, handleEvent: (SeatEvent) -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF161616))
            .padding(20.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Seat(label = areaSeat.label)
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                SeatOperationInfo.entries.forEach { operationInfo ->
                    Box(modifier = Modifier.weight(1f)) {
                        SeatOperation(
                            info = operationInfo,
                            level = seatState.getLevelFor(operationInfo),
                            onClick = {
                                handleEvent(
                                    SeatEvent.SingleSeatOperation(areaSeat, operationInfo)
                                )
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.weight(0.8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                listOf("1", "2", "3", "+").forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        SittingMemoryButton(label = item)
                    }
                }
            }
        }
    }
}

@Composable
fun Seat(label: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.height(70.dp),
            painter = painterResource(id = R.drawable.seat),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = label, color = Color.White, fontSize = 18.sp)
    }
}

@Composable
fun SeatOperation(info: SeatOperationInfo, level: Int, onClick: () -> Unit) {
    val disabled = level == 0
    val disabledColor = Color(0xFF787878)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(
                if (disabled) {
                    Color(0xFF1E1E1E)
                } else {
                    Color(0xFF292929)
                }
            )
            .padding(5.dp, 5.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(
                id = if (disabled) {
                    info.offResId
                } else {
                    info.onResId
                }
            ),
            contentDescription = ""
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(width = 7.dp, height = 3.dp)
                        .background(
                            color = if (index < level) {
                                info.color
                            } else {
                                disabledColor
                            }
                        )
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(info.label, color = Color.White, fontSize = 10.sp)
    }
}

@Composable
fun SittingMemoryButton(label: String) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.number_button),
            contentDescription = ""
        )
        Text(text = label, color = Color.White.copy(alpha = 0.4f), fontSize = 24.sp)
    }
}

enum class AreaSeat(val label: String, val areaId: Int) {
    DRIVER("主驾", 1),
    COPILOT("副驾", 4);

    companion object {
        fun getByAreaId(areaId: Int): AreaSeat? {
            return AreaSeat.entries.find { areaSeat -> areaSeat.areaId == areaId }
        }
    }
}

enum class SeatOperationInfo(
    val label: String,
    val color: Color,
    @DrawableRes val offResId: Int,
    @DrawableRes val onResId: Int,
    val propertyId: Int?,
    val values: List<Int>?
) {
    HEATING(
        "Seat heating",
        Color(0xFFCF597C),
        R.drawable.seat_heating_off,
        R.drawable.seat_heating_on,
        VehiclePropertyIds.HVAC_SEAT_TEMPERATURE,
        listOf(-1, 1, 2)
    ),
    COOLING(
        "Seat cooling",
        Color(0xFF3476E3),
        R.drawable.seat_cooling_off,
        R.drawable.seat_cooling_on,
        VehiclePropertyIds.HVAC_SEAT_VENTILATION,
        null
    ),
    MESSAGE(
        "Message",
        Color(0xFF59CF8F),
        R.drawable.seat_message_off,
        R.drawable.seat_message_on,
        null,
        null
    )
}

@Preview
@Composable
fun SeatControlPreview() {
    Box(modifier = Modifier.height(207.dp)) {
        SeatControl(
            areaSeat = AreaSeat.DRIVER,
            seatState = SingleSeatState(),
            handleEvent = {}
        )
    }
}
