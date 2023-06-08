package com.georgiytitov.boottracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BootEvent(
    @PrimaryKey val bootTime: Long
)