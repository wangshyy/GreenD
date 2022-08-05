package com.android.greendrink.ui.menu.classic

import androidx.lifecycle.ViewModel
import com.android.greendrink.R
import com.android.greendrink.data.Goods

class ClassicViewModel : ViewModel() {
    val goodsGroupList: List<List<Goods>> = listOf(
        listOf(
            Goods(R.drawable.milky_tea, "奶与茶", 16), Goods(R.drawable.sprite, "碧啊碧", 12),
            Goods(R.drawable.lemon_juice, "酸酸水", 12), Goods(R.drawable.milk, "奶牛奶", 18),
            Goods(R.drawable.green_tea, "绿茶", 12), Goods(R.drawable.coffee, "咖啡", 20)
        ),
        listOf(
            Goods(R.drawable.cocktail, "鸡尾酒", 25), Goods(R.drawable.beer, "独家啤酒", 20)
        ),
        listOf(
            Goods(R.drawable.lollipop, "冰棒糖", 2), Goods(R.drawable.popsicle, "菠萝冰棍", 6),
            Goods(R.drawable.ice_cream1, "甜筒", 6), Goods(R.drawable.ice_cream2, "秘制雪糕", 12),
            Goods(R.drawable.ice_cream3, "麦旋风", 18)
        ),
        listOf(
            Goods(R.drawable.bread, "好吃面包", 12), Goods(R.drawable.cake, "蛋糕", 18)
        ),
        listOf(
            Goods(R.drawable.poker, "扑克牌", 2), Goods(R.drawable.imperial_crown, "皇冠", 3)
        ),
        listOf(
            Goods(R.drawable.snowflake, "真的能飘的雪花", 1), Goods(R.drawable.ice_block, "冰块", 0)
        )
    )

    // TODO: Implement the ViewModel
    val tabNameList = listOf("饮品", "微醺", "冰凉", "点心", "玩乐", "配料")

}