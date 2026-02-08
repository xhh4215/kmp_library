package com.example.`kmp-share`

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


val LocalData = compositionLocalOf<State<Color>> { error("Data not provided") }


@Composable
@Preview
fun App() {
    MaterialTheme {
        val stateColor = remember { mutableStateOf(Color.Yellow) }
        LocalTestDemo {
            CompositionLocalProvider(LocalData provides stateColor){
                TextDemo()
            }
        }
    }
}


@Composable
fun LocalTestDemo(content: @Composable () -> Unit) {

    val colorState = remember {
        mutableStateOf(Color.Red)
    }
    CompositionLocalProvider(LocalData provides colorState) {
        content()
    }


}

//无状态
@Preview
@Composable
fun TextDemo(name: String = "Kmp") {
    Text(text = name, color = LocalData.current.value)


}






