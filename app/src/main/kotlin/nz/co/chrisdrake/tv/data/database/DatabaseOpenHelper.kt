package nz.co.chrisdrake.tv.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class DatabaseOpenHelper @Inject constructor(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

  private companion object {
    const val DATABASE_NAME = "tv.db"
    const val DATABASE_VERSION = 1
  }

  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(ChannelModel.CREATE_TABLE)
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    // Do nothing
  }
}