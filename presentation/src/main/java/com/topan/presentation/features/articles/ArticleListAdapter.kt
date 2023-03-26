package com.topan.presentation.features.articles

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.topan.domain.model.ArticleItem
import com.topan.domain.utils.emptyInt
import com.topan.domain.utils.emptyString
import com.topan.presentation.R
import com.topan.presentation.databinding.ItemArticleBinding


/**
 * Created by Topan E on 26/03/23.
 */
class ArticleListAdapter: RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    var articleList = listOf<ArticleItem>()
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
        val view = inflater.inflate(R.layout.item_article, parent, false)
        return ViewHolder(ItemArticleBinding.bind(view))
    }

    override fun getItemCount() = articleList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(article = articleList[position])
    }

    inner class ViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleItem) = with (binding){
            authorTextView.text = article.author
            titleTextView.text = article.title
            showBanner(article.urlToImage ?: emptyString())
            dateTextView.text = article.publishedAt
            article.url?.let(clickListener)
        }

        private fun showBanner(urlToImage: String) {
            if (urlToImage.isEmpty()) return
            Glide.with(binding.root.context)
                .load(urlToImage)
                .into(binding.bannerImageView)
        }
    }
}
