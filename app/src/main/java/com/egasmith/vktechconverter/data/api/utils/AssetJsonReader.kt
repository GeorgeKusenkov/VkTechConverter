package com.egasmith.vktechconverter.data.api.utils

import android.content.Context
import java.io.IOException

interface JsonReader {
    fun getJsonFromAssets(fileName: String): String?
}

class AssetJsonReader(private val context: Context) : JsonReader {
    override fun getJsonFromAssets(fileName: String): String? {
        return try {
            val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
            json
        } catch (ex: IOException) {
            null
        }
    }
}