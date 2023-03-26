package com.topan.presentation.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.topan.domain.model.SourceItem
import com.topan.domain.utils.emptyInt
import com.topan.presentation.R
import com.topan.presentation.databinding.ItemSourceBinding

/**
 * Created by Topan E on 25/03/23.
 */
class SourceListAdapter : RecyclerView.Adapter<SourceListAdapter.ViewHolder>() {
    var sourceList = listOf<SourceItem>()
        set(value) {
            field = value
            notifyItemRangeChanged(emptyInt(), this.itemCount)
        }

    private var clickListener: (String) -> Unit = {}

    fun onItemClickListener(callBack: (String) -> Unit) {
        clickListener = callBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_source, parent, false)
        return ViewHolder(ItemSourceBinding.bind(view))
    }

    override fun getItemCount() = sourceList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(source = sourceList[position])
    }

    inner class ViewHolder(private val binding: ItemSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: SourceItem) = with(binding) {
            titleTextView.text = source.name
            descriptionTextView.text = source.description
            root.setOnClickListener {
                source.id?.let(clickListener)
            }
        }
    }
}
