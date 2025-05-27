package com.example.notes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.notes.ui.theme.NotesTheme

import android.graphics.Color
import java.io.File

class MainActivity : ComponentActivity() {
    private val fileN = FileNotebook()
    private val fileJ by lazy { File(filesDir, "notes.json") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        test()
        setContent {
            NotesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    private fun test() {
        var i = 10
        while(i > 0){
            fileN.addNote(Note(title = "Тест", content = "Тест"))
            i--;
        }
        fileN.addNote(Note(title = "Тест", content = "Тест"))
        fileN.addNote(Note(title = "Тест2", content = "Тест2", importance = Note.Importance.MATTER))
        fileN.addNote(Note(title = "Тест3", content = "Тест3", color = Color.CYAN))
        fileN.saveNote(fileJ)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesTheme {
        Greeting("Android")
    }
}