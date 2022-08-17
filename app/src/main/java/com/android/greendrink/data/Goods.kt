package com.android.greendrink.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goods(
    @PrimaryKey(autoGenerate = true) val goodsId: Int? = 0,
    @ColumnInfo(name = "tab_name") val tabName: String?,
    var imageResource: Int?,
    var name: String?,
    var price: Int?
)