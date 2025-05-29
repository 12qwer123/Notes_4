package com.example.notes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.notes.ui.theme.NotesTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.io.File

class MainActivity : ComponentActivity() {
    private val fileNotebook = FileNotebook()
    private val fileJ by lazy { File(filesDir, "notes1.json") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fileNotebook.loadNote(fileJ)
        setContent {
            NotesTheme {
                Surface(modifier = Modifier.fillMaxSize()) { NotesApp(fileNotebook, fileJ)}
            }
        }
    }
}

@Composable
fun NotesApp(fileNotebook: FileNotebook, fileJ: File) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "notesList"
    ) {
        composable("notesList") {
            ScreenList(
                notes = fileNotebook.notes,
                onNoteClick = { note -> navController.navigate("editNote/${note.uid}")},
                onAddNote = { navController.navigate("editNote/new")},
                onDeleteNote = { note -> fileNotebook.deleteNote(note.uid)
                    fileNotebook.saveNote(fileJ)
                }
            )
        }

        composable("editNote/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: "new"
            val note = if (noteId == "new") {
                Note(title = "", content = "")
            } else {
                fileNotebook.notes.find { it.uid == noteId } ?: Note(title = "", content = "")
            }

            ScreenNote(
                note = note,
                onSaveClick = { updatedNote ->
                    if (noteId == "new") {
                        fileNotebook.addNote(updatedNote)
                    } else {
                        fileNotebook.deleteNote(noteId)
                        fileNotebook.addNote(updatedNote)
                    }
                    fileNotebook.saveNote(fileJ)
                    navController.popBackStack()
                },
                onCancelClick = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesAppPreview() {
    NotesTheme {
        NotesApp(FileNotebook(), File(""))
    }
}