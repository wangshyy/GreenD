package com.android.greendrink.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.greendrink.data.Goods

/*
 GoodsDao提供与数据表进行交互的各种方法
 */
@Dao
interface GoodsDao {
    //插入一组数据
    @Insert
    fun insertAll(vararg goods: Goods)

    //插入单个数据
    @Insert
    fun insertGoods(goods: Goods)

    //根据标签名称查询
    @Query("SELECT * FROM goods WHERE tab_name = (:tabName)")
    fun queryGoodsByTabName(tabName: String): List<Goods>

    //查询全部数据
    @Query("SELECT * FROM goods")
    fun getAll(): List<Goods>
}