package com.example.base.testutils

import com.squareup.moshi.Moshi
import moe.banana.jsonapi2.Resource
import moe.banana.jsonapi2.ResourceAdapterFactory

object TestUtils {

    fun jsonApiMoshi(vararg clazzes: Class<out Resource>): Moshi {
        val builder = ResourceAdapterFactory
                .builder()

        clazzes.forEach {
            builder.add(it)
        }

        val jsonApiAdapterFactory = builder.build()


        return Moshi
                .Builder()
                .add(jsonApiAdapterFactory)
                .build()
    }

}