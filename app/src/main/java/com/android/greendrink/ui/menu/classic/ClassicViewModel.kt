package com.android.greendrink.ui.menu.classic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.greendrink.R
import com.android.greendrink.data.Goods
import com.android.greendrink.db.GoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Array.get

class ClassicViewModel : ViewModel() {
    private val _goodsGroupList: MutableLiveData<MutableList<List<Goods>>> = MutableLiveData()

    fun getGoodsGroupList(goodsDao: GoodsDao): LiveData<MutableList<List<Goods>>> {
        queryAllGoodsByTabName(goodsDao)
        return _goodsGroupList
    }

    val tabNameList = arrayListOf("饮品", "微醺", "冰凉", "点心", "玩乐", "配料")
    private val goodsList: Array<Goods> = arrayOf(
        Goods(null, "饮品", R.drawable.milky_tea, "奶茶", 16),
        Goods(null, "饮品", R.drawable.sprite, "雪碧", 12),
        Goods(null, "饮品", R.drawable.lemon_juice, "柠檬水", 12),
        Goods(null, "饮品", R.drawable.milk, "牛奶", 18),
        Goods(null, "饮品", R.drawable.green_tea, "绿茶", 12),
        Goods(null, "饮品", R.drawable.coffee, "咖啡", 20),
        Goods(null, "微醺", R.drawable.cocktail, "鸡尾酒", 25),
        Goods(null, "微醺", R.drawable.beer, "啤酒", 20),
        Goods(null, "冰凉", R.drawable.lollipop, "冰棒糖", 2),
        Goods(null, "冰凉", R.drawable.popsicle, "菠萝冰棍", 6),
        Goods(null, "冰凉", R.drawable.ice_cream1, "甜筒", 6),
        Goods(null, "冰凉", R.drawable.ice_cream2, "雪糕", 12),
        Goods(null, "冰凉", R.drawable.ice_cream3, "麦旋风", 18),
        Goods(null, "点心", R.drawable.bread, "面包", 12),
        Goods(null, "点心", R.drawable.cake, "蛋糕", 18),
        Goods(null, "玩乐", R.drawable.poker, "扑克牌", 2),
        Goods(null, "玩乐", R.drawable.imperial_crown, "皇冠", 3),
        Goods(null, "配料", R.drawable.snowflake, "雪花", 1),
        Goods(null, "配料", R.drawable.ice_block, "冰块", 0)
    )

    fun insertAll(goodsDao: GoodsDao) = viewModelScope.launch(Dispatchers.IO) {
        goodsDao.insertAll(*goodsList)
    }

    //按标签分类查找
    private fun queryAllGoodsByTabName(goodsDao: GoodsDao) {
        Log.d("qwer", "开始")
        val list: MutableList<List<Goods>> = mutableListOf()
        viewModelScope.launch {
            Log.d("qwer", "协程开始")
            withContext(Dispatchers.IO) {
                tabNameList.forEach {
                    list.add(goodsDao.queryGoodsByTabName(it))
                }
            }
            Log.d("qwer", "协程结束")
            _goodsGroupList.value = mutableListOf()
            _goodsGroupList.value?.addAll(list)
            _goodsGroupList.postValue(_goodsGroupList.value)
        }
        Log.d("qwer", "${_goodsGroupList.value?.size}")
        Log.d("qwer", "结束")
    }
}