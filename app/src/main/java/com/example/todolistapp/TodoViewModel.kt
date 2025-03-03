package com.example.todolistapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.todolistapp.model.TodoItem

class TodoViewModel : ViewModel() {
    private var nextId = 0
    val todoItems = mutableStateListOf<TodoItem>()

    fun addTask(task: String) {
        todoItems.add(TodoItem(id = nextId++, task = task, isDone = false))
    }

    fun toggleTaskDone(itemId: Int) {
        val item = todoItems.find { it.id == itemId }
        item?.let {
            it.isDone = !it.isDone
        }
    }

    fun removeTask(itemId: Int) {
        todoItems.removeAll { it.id == itemId }
    }
}
