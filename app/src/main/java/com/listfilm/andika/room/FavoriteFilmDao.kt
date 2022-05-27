package com.listfilm.andika.room

import androidx.room.*

@Dao
interface FavoriteFilmDao {
    @Insert
    fun addFilm (favoriteFilm : FavoriteFilm) : Long
    @Delete
    fun deleteFav(favoriteFilm: FavoriteFilm ):Int
    @Query("SELECT * FROM Fav WHERE Fav.email = :email  AND Fav.title = :title")
    fun checkFav(email: String,  title : String): List<FavoriteFilm>
    @Query("SELECT * FROM Fav WHERE Fav.email = :email")
    fun getFav(email: String): List<FavoriteFilm>
    @Query("DELETE FROM Fav WHERE Fav.email = :email  AND Fav.title = :title")
    fun deleteFilmID(email :String, title : String): Int
    @Query("SELECT *  FROM Fav")
    fun getAllFav(): List<FavoriteFilm>

}