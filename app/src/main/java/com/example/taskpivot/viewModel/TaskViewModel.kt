import android.app.Application
import androidx.lifecycle.AndroidViewModel

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository = TaskRepository(application)

    fun addTask(task: Task): Long {
        return repository.addTask(task)
    }

    fun getAllTasks(): List<Task> {
        return repository.getAllTasks()
    }

    fun deleteTask(taskId: Int): Int {
        return repository.deleteTask(taskId)
    }

    fun updateTaskPriority(taskId: Int, newPriority: Boolean): Int {
        return repository.updateTaskPriority(taskId, newPriority)
    }

    fun getTaskById(taskId: Int): Task? {
        return repository.getTaskById(taskId)
    }
}
