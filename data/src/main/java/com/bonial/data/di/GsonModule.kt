package com.bonial.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GsonModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            // custom deserializer to handle polymorphic content field
            .registerTypeAdapter(Any::class.java, JsonDeserializer { json, _, _ ->
                if (json.isJsonArray) {
                    return@JsonDeserializer json.asJsonArray
                } else if (json.isJsonObject) {
                    return@JsonDeserializer json.asJsonObject
                } else if (json.isJsonPrimitive) {
                    val primitive = json.asJsonPrimitive
                    when {
                        primitive.isBoolean -> return@JsonDeserializer primitive.asBoolean
                        primitive.isNumber -> return@JsonDeserializer primitive.asNumber
                        else -> return@JsonDeserializer primitive.asString
                    }
                } else {
                    return@JsonDeserializer null
                }
            })
            .create()
    }
}