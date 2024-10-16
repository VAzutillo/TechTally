package com.example.techtally

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewsAdapter(private var reviews: MutableList<Review>) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameOfTheUser: TextView = itemView.findViewById(R.id.nameOfTheUser)
        private val rateOfTheUser: TextView = itemView.findViewById(R.id.rateOfTheUser)
        private val commentOfTheUser: TextView = itemView.findViewById(R.id.commentOfTheUser)

        fun bind(review: Review) {
            nameOfTheUser.text = review.username
            rateOfTheUser.text = review.rating.toString()
            commentOfTheUser.text = review.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.samsung_galaxy_s24_review_item_layout, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    // Method to add a new review to the list and notify the adapter
    fun addReview(review: Review) {
        reviews.add(review)
        notifyItemInserted(reviews.size - 1) // Notify that a new item has been added
    }
}