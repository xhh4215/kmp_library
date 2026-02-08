package com.luanxh.shared.navigation.demo.sheet

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.shared.navigation.demo.content.ContentBlue
import com.example.shared.navigation.demo.content.ContentGreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
private data object RouteA : NavKey {

}

@Serializable
private class RouteB(val id: String) : NavKey {

}


private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(RouteA::class, RouteA.serializer())
            subclass(RouteB::class, RouteB.serializer())
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetActivity() {
    val backStack = rememberNavBackStack(config, RouteA)
    val bottomSheetStrategy = remember {
        ModalBottomSheetStrategy<NavKey>()
    }

    NavDisplay(
        backStack = backStack,
        sceneStrategy = bottomSheetStrategy,
        entryProvider = entryProvider {
            entry<RouteA> {
                ContentGreen("welcome to nav3"){
                    Button(onClick = {
           backStack.add(RouteB("123"))
                    }){
                        Text("click to open bottom sheet")
                    }
                }
            }

            entry<RouteB>(
                metadata = ModalBottomSheetStrategy.bottomSheet()
            ) {  key->
                ContentBlue(
                    title = "Route id: ${key.id}",
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(16.dp)
                    )
                )
            }
        }
    )
}