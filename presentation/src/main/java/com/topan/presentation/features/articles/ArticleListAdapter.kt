package com.topan.presentation.features.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.topan.domain.model.ArticleItem
import com.topan.domain.utils.emptyString
import com.topan.presentation.R
import com.topan.presentation.databinding.ItemArticleBinding

/**
 * Created by Topan E on 26/03/23.
 */
class ArticleListAdapter: ListAdapter<ArticleItem, ArticleListAdapter.ViewHolder>(REPO_COMPARATOR) {

    private var clickListener: (String) -> Unit = {}

    fun onItemClickListener(callBack: (String) -> Unit) {
        clickListener = callBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_article, parent, false)
        return ViewHolder(ItemArticleBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleItem) = with (binding){
            authorTextView.text = article.source?.name
            titleTextView.text = article.title
            showBanner(article.urlToImage ?: emptyString())
            dateTextView.text = article.publishedAt
            root.setOnClickListener {
                article.url?.let(clickListener)
            }
        }

        private fun showBanner(urlToImage: String) {
            if (urlToImage.isEmpty()) return
            Glide.with(binding.root.context).load(urlToImage).into(binding.bannerImageView)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ArticleItem>() {
            override fun areItemsTheSame(
                oldItem: ArticleItem,
                newItem: ArticleItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ArticleItem,
                newItem: ArticleItem
            ): Boolean = oldItem == newItem
        }
    }
}
