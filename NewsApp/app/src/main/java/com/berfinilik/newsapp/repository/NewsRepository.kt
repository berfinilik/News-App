package com.berfinilik.newsapp.repository

import android.util.Log
import com.berfinilik.newsapp.api.RetrofitInstance
import com.berfinilik.newsapp.db.ArticleDatabase
import com.berfinilik.newsapp.models.Article


class NewsRepository(val db: ArticleDatabase) {

    suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNumber).also { response ->
            if (response.isSuccessful) {
                response.body()?.articles?.forEach { article ->
                    Log.d("API Response", "Title: ${article.title}, Image URL: ${article.urlToImage}")
                }
            } else {
                Log.e("API Error", "Error fetching headlines: ${response.message()}")
            }
        }

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber).also { response ->
            if (response.isSuccessful) {
                response.body()?.articles?.forEach { article ->
                    Log.d("API Response", "Title: ${article.title}, Image URL: ${article.urlToImage}")
                }
            } else {
                Log.e("API Error", "Error searching news: ${response.message()}")
            }
        }

    suspend fun upsert(article: Article) = db.getarticleDao().upsert(article)

    fun getFavouriteNews() = db.getarticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getarticleDao().deleteArticle(article)
}