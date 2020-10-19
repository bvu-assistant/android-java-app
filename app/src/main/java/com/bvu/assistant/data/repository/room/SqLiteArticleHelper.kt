package com.bvu.assistant.data.repository.room

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.bvu.assistant.data.repository.article.Article
import com.bvu.assistant.data.model.Article.SavedArticle
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.bvu.assistant.data.repository.room.SqLiteArticleHelperCallback as SqLiteArticleHelperCallback1


private const val DB_NAME: String = "ARTICLE";
private const val DB_VERSION: Int = 1;
private const val TABLE_NAME: String = "FAVORITE_ARTICLE";
private const val TAG: String = "SqLiteArticleHelper"


interface SqLiteArticleHelperCallback {
    fun onDatabaseTransactionSuccess(message: String)
    fun onDatabaseTransactionFailure(message: String)
}



public class SqLiteArticleHelper(private var context: Context, private var receiver: SqLiteArticleHelperCallback1? = null) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            Log.i(TAG, "onCreate: $DB_NAME")

            db?.execSQL("""
                CREATE TABLE $TABLE_NAME (
                    ID              INTEGER,
                    TYPE            TEXT        CHECK(TYPE IN ("Trang Chủ ",
                                                                "Tin tức Sinh viên - Học viên",
                                                                "Học bổng - Khen thưởng",
                                                                "Hoạt động SV",
                                                                "Sau Đại học",
                                                                "Quy Định Đào tạo",
                                                                "Biểu mẫu SV",
                                                                "Quy định Công tác Sinh viên",
                                                                "Các Hướng Dẫn cho sinh viên")),
                    TITLE           TEXT        NOT NULL,
                    URL             TEXT        NOT NULL,
                    DATE            TEXT        NOT NULL,
                    ADDED_TIME      TEXT        NOT NULL
                )
            """.trimIndent())

            receiver?.onDatabaseTransactionSuccess("Create $TABLE_NAME database successfully")
        }
        catch (ex: Exception) {
            receiver?.onDatabaseTransactionFailure("Create $TABLE_NAME database failed")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i(TAG, "onUpgrade: $DB_NAME")

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        //  Create the Tables again
        onCreate(db)
    }


    @SuppressLint("SimpleDateFormat")
    fun addArticle(article: Article) : Boolean {
        Log.i(TAG, "adding Article: " + article.type)

        try {
            val db = writableDatabase
            val values = ContentValues()
            values.put("ID", "${article.id}")
            values.put("TYPE", article.type)
            values.put("TITLE", article.title)
            values.put("URL", article.url)
            values.put("DATE", article.date)
            values.put("ADDED_TIME", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))

            db.insert(TABLE_NAME, null, values)
            db.close()

            receiver?.onDatabaseTransactionSuccess("Added successfully")
            return true
        }
        catch (ex: java.lang.Exception) {
            Log.e(TAG, "addArticle: failed", ex)
            receiver?.onDatabaseTransactionFailure("Add failed")
            return false
        }
    }


    fun getArticle(articleId: Int): Article? {
        //  Log.i(TAG, "getting Article Id: $articleId")

        try {
            val db: SQLiteDatabase = readableDatabase
            val cursor = db.query(
                    TABLE_NAME,
                arrayOf("ID", "TITLE", "URL", "DATE", "ADDED_TIME"),
                "ID = ?",
                arrayOf("${articleId}"),
                null, null, null, null
            )

            var article: SavedArticle? = null
            cursor?.moveToFirst()
            if (cursor != null && cursor.count > 0) {
                //  Log.d(TAG, "getArticle cursor count: " +  cursor.columnCount)
                article = SavedArticle(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(2),
                    false,
                    true,
                    cursor.getString(4)
                )
            }

            cursor.close()
            db.close()

            receiver?.onDatabaseTransactionSuccess("Get article successfully")
            return article
        }
        catch (ex: Exception) {
            Log.e(TAG, "getArticle: ", ex)
            receiver?.onDatabaseTransactionFailure("Get article failed")
            return null
        }
    }


    @SuppressLint("Recycle")
    fun getAllArticles(): List<SavedArticle> {
        val result = ArrayList<SavedArticle>()

        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)


        //  Loop through all Rows
        if (cursor.moveToFirst()) {
            do {
                result.add(SavedArticle(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    false,
                    true,
                    cursor.getString(5)
                ))
            }
            while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return result
    }


    fun deleteArticle(articleId: Int): Boolean {
        return try {
            val db: SQLiteDatabase = writableDatabase
            db.delete(TABLE_NAME, "ID = ?", arrayOf("${articleId}"))
            db.close()

            receiver?.onDatabaseTransactionSuccess("Delete article successfully")
            true
        }
        catch (ex: Exception) {
            receiver?.onDatabaseTransactionFailure("Delete article failed")
            false
        }
    }

}