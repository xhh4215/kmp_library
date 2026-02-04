package com.example.kmp_library

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shared.navigation.demo.basic.BasicActivity
import com.example.shared.navigation.demo.commonui.CommonUiActivity
import com.example.shared.navigation.demo.multiplestacks.MultipleStacksActivity
import com.example.shared.navigation.demo.sheet.BottomSheetActivity


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






