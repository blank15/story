package com.blank.home

import com.blank.model.database.StoryModel
import net.bytebuddy.utility.RandomString

object DummyData {
    fun generateData(): List<StoryModel> {
        val items: MutableList<StoryModel> = arrayListOf()
        for (i in 0..100) {
            items.add(
                StoryModel(
                    id = i.toString(),
                    name = RandomString.make(),
                    photoUrl = "${RandomString.make()} $i",
                    description = RandomString.make(),
                    lat = i.toDouble(),
                    lon = i.toDouble()
                )
            )
        }
        return items
    }
}