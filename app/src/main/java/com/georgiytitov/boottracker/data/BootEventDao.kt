package com.georgiytitov.boottracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BootEventDao {
    @Query ("SELECT * FROM bootevent")
    fun getAllBootEventsLive(): LiveData<List<BootEvent>>

    @Query ("SELECT * FROM bootevent")
    fun getAllBootEvents(): List<BootEvent>

    @Insert
    fun insertBootEvent(bootEvent: BootEvent)
}
