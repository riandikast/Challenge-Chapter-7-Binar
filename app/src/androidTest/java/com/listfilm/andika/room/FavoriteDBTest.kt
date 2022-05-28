package com.listfilm.andika.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteDBTest : TestCase() {

    private lateinit var db :FavoriteDB
    private lateinit var dao:FavoriteFilmDao
    @Before
    public override fun setUp() {
        val context  = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, FavoriteDB::class.java).build()
        dao = db.getFavoriteDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun getFavoriteDao() {
        dao.getAllFav()
    }
}