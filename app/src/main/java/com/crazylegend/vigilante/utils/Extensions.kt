@file:Suppress("DEPRECATION")

package com.crazylegend.vigilante.utils

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.crazylegend.kotlinextensions.activity.newIntent
import com.crazylegend.kotlinextensions.services.isServiceRunning
import com.crazylegend.kotlinextensions.services.startForegroundService
import com.crazylegend.vigilante.VigilanteService
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

/**
 * Created by crazy on 10/14/20 to long live and prosper !
 */


const val VIGILANTE_DB_NAME = "vigilante-db"

fun Context.startVigilante() {
    startForegroundService<VigilanteService>()
}

fun Context.stopVigilante(): Boolean {
    val intent = newIntent<VigilanteService>(this)
    return stopService(intent)
}

fun Context.isVigilanteRunning() = isServiceRunning<VigilanteService>()

inline fun <reified T : RoomDatabase> Context.instantiateDatabase(cameraDbName: String): T {
    val passphrase: ByteArray = SQLiteDatabase.getBytes(packageName.toCharArray())
    val factory = SupportFactory(passphrase)
    return Room.databaseBuilder(this, T::class.java, cameraDbName)
            .openHelperFactory(factory)
            .build()
}