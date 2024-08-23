package com.harsh.androidnetworkingapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecViewAdapter : RecyclerView.Adapter<RecViewAdapter.ViewHolder>{

    private var arrNewsArticle:ArrayList<NewsArticlesModel> = ArrayList()
    private var context: Context? = null

    constructor(context: Context,arrNewsArticle:ArrayList<NewsArticlesModel>) : super(){
        this.context=context
        this.arrNewsArticle=arrNewsArticle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.row_rec_view,parent,false))
    }


    override fun onBindViewHolder(holder: RecViewAdapter.ViewHolder, position: Int) {
        holder.tvAuthor.text=arrNewsArticle[position].author
        holder.tvTitle.text=arrNewsArticle[position].title
        holder.tvDescription.text=arrNewsArticle[position].description
        holder.tvUrl.text=arrNewsArticle[position].url
        holder.tvUrlToImage.text=arrNewsArticle[position].urlToImage
        holder.tvPublishedAt.text=arrNewsArticle[position].publishedAt
        holder.tvContent.text=arrNewsArticle[position].content
    }

    override fun getItemCount(): Int {
        return arrNewsArticle.size
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor : TextView = itemView.findViewById(R.id.tvAuthor)
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription : TextView = itemView.findViewById(R.id.tvDescription)
        val tvUrl : TextView = itemView.findViewById(R.id.tvUrl)
        val tvUrlToImage : TextView = itemView.findViewById(R.id.tvUrlToImage)
        val tvPublishedAt : TextView = itemView.findViewById(R.id.tvPublishedAt)
        val tvContent : TextView = itemView.findViewById(R.id.tvContent)
    }
}