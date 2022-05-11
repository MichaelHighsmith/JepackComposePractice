package com.michaelhighsmith.jepackcomposepractice

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Greeting("Mike")
            HelloScreen()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun TextButton() {
 /*   Box(modifier = Modifier.clickable(onClick = {
        Text(text = text)
    }))*/
}

class HelloViewModel : ViewModel() {
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChange(newName: String) {
        _name.value = newName
    }
}

@Composable
fun HelloScreen(helloViewModel: HelloViewModel = viewModel()) {
    val name: String by helloViewModel.name.observeAsState("")
    HelloContent(name = name, onNameChange = { helloViewModel.onNameChange(it) })
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    val intent = Intent(LocalContext.current, NotificaitonSettingsActivity::class.java)
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5
            )
        }
        OutlinedTextField(value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )

        Button(onClick = {
            startActivity(context, intent, null) }
        ) {
            Text("Notification Settings")
        }
    }
}
