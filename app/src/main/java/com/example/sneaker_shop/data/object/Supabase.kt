package com.example.sneaker_shop.data.`object`

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json

object Supabase {

    private const val SUPABASE_URL = "https://ujagqcqzxqgrfsseueip.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVqYWdxY3F6eHFncmZzc2V1ZWlwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU5MzE2MDIsImV4cCI6MjA4MTUwNzYwMn0.aCayPOGM3_gmGVIawGqvPyoLZA5IkREI1gKtWkzrmBw"

    val client: SupabaseClient by lazy {
        println("Initializing Supabase with URL: $SUPABASE_URL")
        println("Key length: ${SUPABASE_KEY.length}")

        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        ) {
            defaultSerializer = KotlinXSerializer(Json {
                ignoreUnknownKeys = true
            })
            install(Postgrest)
            install(GoTrue)
        }
    }
}