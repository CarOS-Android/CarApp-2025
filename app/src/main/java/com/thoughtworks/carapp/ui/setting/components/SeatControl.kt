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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.setting.viewmodel.SeatViewModel

@Composable
fun SeatControl(areaSeat: AreaSeat) {
    val seatViewModel: SeatViewModel = hiltViewModel()
    val seatState =
        seatViewModel.operationState.collectAsState().value.getSeatState(areaSeat)

    Row(
        modifier = Modifier
            .size(width = 605.dp, height = 283.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF161616))
            .padding(45.dp, 32.dp, 24.dp, 32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Seat(label = areaSeat.label)
        Spacer(modifier = Modifier.width(54.dp))
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                SeatOperationInfo.entries.forEach { operationInfo ->
                    SeatOperation(
                        info = operationInfo,
                        level = seatState.getLevelFor(operationInfo),
                        onClick = {
                            seatViewModel.setSeatValue(
                                areaSeat,
                                operationInfo
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(42.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                listOf("1", "2", "3", "+").forEach { item ->
                    SittingMemoryButton(label = item)
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
            modifier = Modifier.height(111.dp),
            painter = painterResource(id = R.drawable.seat),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = label, color = Color.White, fontSize = 28.sp)
    }
}

@Composable
fun SeatOperation(info: SeatOperationInfo, level: Int, onClick: () -> Unit) {
    val disabled = level == 0
    val disabledColor = Color(0xFF787878)

    Column(
        modifier = Modifier
            .size(width = 110.dp, height = 96.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                if (disabled) {
                    Color(0xFF1E1E1E)
                } else {
                    Color(0xFF292929)
                }
            )
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(43.dp),
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
        modifier = Modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
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
