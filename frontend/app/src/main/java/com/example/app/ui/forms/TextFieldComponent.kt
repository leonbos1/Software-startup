package com.example.app.ui.forms

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.app.presentation.states.TextFieldState
import com.example.app.ui.theme.LightGray
import com.example.app.ui.theme.White

@Composable
fun TextFieldComponent(state: TextFieldState, label: String, onTextChanged: (String) -> Unit) {

    TextField(
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        label = { Text(text = label) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = White,
            focusedContainerColor = White,
            unfocusedIndicatorColor = LightGray,
            focusedIndicatorColor = Color.Transparent
        ),
        isError = state.error != null,
        keyboardOptions = KeyboardOptions.Default,
        value = state.text,
        onValueChange = {
            onTextChanged(it)
        }
    )
    if (state.error != "") {
        androidx.compose.material.Text(
            text = state.error ?: "",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(5.dp))
}