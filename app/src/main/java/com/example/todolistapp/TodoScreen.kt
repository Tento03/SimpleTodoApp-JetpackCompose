package com.example.todolistapp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistapp.model.TodoItem

@Composable
fun TodoApp(viewModel: TodoViewModel = viewModel()) {
    var newTaskText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "ToDo List", style = MaterialTheme.typography.titleMedium)

        // Input untuk task baru
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BasicTextField(
                value = newTaskText,
                onValueChange = { newTaskText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )
            Button(onClick = {
                if (newTaskText.isNotEmpty()) {
                    viewModel.addTask(newTaskText)
                    newTaskText = ""
                }
            }, modifier = Modifier.padding(start = 8.dp)) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daftar ToDo
        TodoList(
            todoItems = viewModel.todoItems,
            onTaskToggle = { viewModel.toggleTaskDone(it) },
            onTaskRemove = { viewModel.removeTask(it) }
        )
    }
}

@Composable
fun TodoList(
    todoItems: List<TodoItem>,
    onTaskToggle: (Int) -> Unit,
    onTaskRemove: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(todoItems, key = { it.id }) { todoItem ->  // Menggunakan key agar Compose tahu item mana yang berubah
            TodoItemRow(
                todoItem = todoItem,
                onToggle = { onTaskToggle(todoItem.id) },
                onRemove = { onTaskRemove(todoItem.id) }
            )
        }
    }
}

@Composable
fun TodoItemRow(todoItem: TodoItem, onToggle: () -> Unit, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = todoItem.task,
            style = if (todoItem.isDone)
                MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
            else
                MaterialTheme.typography.bodyMedium
        )

        Row {
            Checkbox(
                checked = todoItem.isDone,
                onCheckedChange = { onToggle() }  // Langsung memicu toggle tanpa delay
            )
            IconButton(onClick = { onRemove() }) {
                Icon(Icons.Default.Delete, contentDescription = "Remove")
            }
        }
    }
}

// Preview untuk melihat bagaimana tampilannya
@Preview(showBackground = true)
@Composable
fun PreviewTodoApp() {
    TodoApp()
}
