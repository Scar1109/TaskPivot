import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TaskDB"
        const val TABLE_TASKS = "tasks"
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_DESCRIPTION = "description"
        const val KEY_PRIORITY = "priority"
        const val KEY_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_TASKS " +
                "($KEY_ID INTEGER PRIMARY KEY," +
                " $KEY_TITLE TEXT," +
                " $KEY_DESCRIPTION TEXT," +
                " $KEY_PRIORITY INTEGER," +
                " $KEY_DATE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }
}
