package ru.haroncode.wordlearn.mainflow.ui.learn

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun LearnScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { }
        ) {
            Text(text = "first button")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = { }
        ) {
            Text(text = "second button")
        }
    }

}