package com.topan.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.forEach
import com.google.android.material.tabs.TabLayout

/**
 * Created by Topan E on 25/03/23.
 */
class ScalableTabLayout : TabLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val tabLayout = getChildAt(0) as ViewGroup
        val childCount = tabLayout.childCount
        if (childCount > 0) {
            val widthPixels = MeasureSpec.getSize(widthMeasureSpec)
            val tabMinWidth = widthPixels / childCount
            tabLayout.forEach { it.minimumWidth = tabMinWidth }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
