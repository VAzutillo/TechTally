package com.example.techtally

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val username: String,
    val rating: Int,
    val comment: String
) : Parcelable