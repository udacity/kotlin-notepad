package com.udacity.notepad.data

import java.util.*

data class Note(
    var id:Int = -1,
    var text: String? = null,
    var isPinned:Boolean = false,
    var createdAt:Date = Date(),
    var updatedAt: Date? = null
)
