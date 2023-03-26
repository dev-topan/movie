package com.topan.presentation.utils

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Topan E on 26/03/23.
 */
fun RecyclerView.onLoadMore(action: () -> Unit) {
    val linearLayoutManager = layoutManager as androidx.recyclerview.widget.LinearLayoutManager

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)
            val totalItemCount = linearLayoutManager.itemCount
            val visibleItemCount = linearLayoutManager.childCount
            val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
            if (visibleItemCount + lastVisibleItem + 5 >= totalItemCount) {
                action.invoke()
            }
        }
    })
}