package com.thoughtworks.carapp.ui.setting.presentation

import com.thoughtworks.carapp.ui.setting.components.AreaSeat
import com.thoughtworks.carapp.ui.setting.components.SeatOperationInfo

// 座椅控制事件，定义用户在 UI 上的操作
sealed class SeatEvent {
    // 单个座椅操作事件
    data class SingleSeatOperation(
        val seat: AreaSeat,
        val operation: SeatOperationInfo
    ) : SeatEvent()
}