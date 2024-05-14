import androidx.room.PrimaryKey

data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val taskTitle: String,
    val taskDescription: String,
    val taskPriority: Boolean,
    val taskDate: String
)
