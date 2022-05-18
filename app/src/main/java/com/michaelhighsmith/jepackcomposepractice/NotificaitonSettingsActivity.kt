package com.michaelhighsmith.jepackcomposepractice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.imageResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.OutlinedTextFieldDecorationBox
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.sp


class NotificaitonSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EmailScreen()
        }
    }

    @Preview
    @Composable
    fun NotificationSettingsPreview() {
        EmailScreen()
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(id = R.drawable.email_icon),
                "",
                contentScale = ContentScale.Fit
            )
            CustomTextField( fontSize = 12.sp,
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.surface,
                        RoundedCornerShape(percent = 50)
                    )
                    .padding(4.dp)
                    .height(20.dp),

                email = email, onEmailChange = onEmailChange)

        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(id = R.drawable.email_icon),
                "",
                contentScale = ContentScale.Fit
            )
            OutlinedTextField(value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
               // modifier = Modifier.height(30.dp),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Row {
            EmailCheckBox()
            Text(
                text = "Yes, dfdfd",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5
            )
        }

    }


}@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    email: String,
    onEmailChange: (String) -> Unit
) {
    MyTextField(email = email, onEmailChange = onEmailChange)
/*    BasicTextField(
        value = email,
        onValueChange = onEmailChange,
        Modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .background(Color.White, RoundedCornerShape(percent = 10))

            ) {

                if (email.isEmpty()) {
                    Text("Label")
                }
                innerTextField()  //<-- Add this
            }
        },
    )*/
   /* OutlinedTextField(value = email,
        onValueChange = onEmailChange,
        Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
        focusedIndicatorColor =  Color.Green, //hide the indicator
        unfocusedIndicatorColor = Color.Black))*/

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyTextField(email: String,
                onEmailChange: (String) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    // parameters below will be passed to BasicTextField for correct behavior of the text field,
    // and to the decoration box for proper styling and sizing
    val enabled = true
    val singleLine = true

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        unfocusedBorderColor = Color.LightGray,
        focusedBorderColor = Color.Blue
    )
    BasicTextField(
        value = email,
        onValueChange = onEmailChange,
       // modifier = modifier,
        // internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine
    ) {
        OutlinedTextFieldDecorationBox(
            value = email,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            // same interaction source as the one passed to BasicTextField to read focus state
            // for text field styling
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                start = 4.dp, end = 4.dp, top = 2.dp, bottom = 3.dp
            ),
            // update border thickness and shape
            border = {
                TextFieldDefaults.BorderBox(
                    enabled = enabled,
                    isError = false,
                    colors = colors,
                    interactionSource = interactionSource,
                    shape = RoundedCornerShape(percent = 20),
                    unfocusedBorderThickness = 2.dp,
                    focusedBorderThickness = 2.dp
                )
            },
            // update border colors
            colors = colors
        )
    }
}

@Composable
fun EmailCheckBox() {
    val checkedState = remember { mutableStateOf(true) }
    Checkbox(
        checked = checkedState.value,
        onCheckedChange = { checkedState.value = it },
        colors = CheckboxDefaults.colors(Color.Blue)

    )
}
