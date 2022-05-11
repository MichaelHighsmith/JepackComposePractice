package com.michaelhighsmith.jepackcomposepractice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.imageResource
import androidx.compose.foundation.Image
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


class NotificaitonSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EmailScreen()
        }
    }
}

class EmailViewModel : ViewModel() {
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }
}

@Composable
fun EmailScreen(emailViewModel: EmailViewModel = viewModel()) {
    val email: String by emailViewModel.email.observeAsState("")
    EmailLayout(email = email, onEmailChange = { emailViewModel.onEmailChange(it) })
}

@Composable
fun EmailLayout(email: String, onEmailChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Email",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.h5
        )
        Row {
            Image(painterResource(id = R.drawable.ic_launcher_background), "")
            OutlinedTextField(value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") }
            )
        }
        
    }

}
