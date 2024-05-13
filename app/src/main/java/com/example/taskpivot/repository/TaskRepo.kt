import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class TaskRepository(context: Context) {

    private val dbHelper: TaskDBHelper = TaskDBHelper(context)

    fun addTask(task: Task): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(TaskDBHelper.KEY_TITLE, task.taskTitle)
        values.put(TaskDBHelper.KEY_DESCRIPTION, task.taskDescription)
        values.put(TaskDBHelper.KEY_PRIORITY, if (task.taskPriority) 1 else 0)
        values.put(TaskDBHelper.KEY_DATE, task.taskDate)
        return db.insert(TaskDBHelper.TABLE_TASKS, null, values)
    }

    @SuppressLint("Range")
    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${TaskDBHelper.TABLE_TASKS}", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(TaskDBHelper.KEY_ID))
                val title = cursor.getString(cursor.getColumnIndex(TaskDBHelper.KEY_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(TaskDBHelper.KEY_DESCRIPTION))
                val priority = cursor.getInt(cursor.getColumnIndex(TaskDBHelper.KEY_PRIORITY)) == 1
                val date = cursor.getString(cursor.getColumnIndex(TaskDBHelper.KEY_DATE))
                val task = Task(id, title, description, priority, date)
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tasks
    }

    fun deleteTask(taskId: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(TaskDBHelper.TABLE_TASKS, "${TaskDBHelper.KEY_ID}=?", arrayOf(taskId.toString()))
    }

    fun updateTaskPriority(taskId: Int, newPriority: Boolean): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TaskDBHelper.KEY_PRIORITY, if (newPriority) 1 else 0)
        }
        return db.update(
            TaskDBHelper.TABLE_TASKS,
            values,
            "${TaskDBHelper.KEY_ID}=?",
            arrayOf(taskId.toString())
        )
    }

}
