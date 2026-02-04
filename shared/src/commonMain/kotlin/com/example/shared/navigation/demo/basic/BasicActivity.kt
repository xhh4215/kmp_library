package com.example.shared.navigation.demo.basic

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.shared.navigation.demo.content.ContentOrange
import com.example.shared.navigation.demo.content.ContentRed
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
private data object RouteA : NavKey

@Serializable
private data class RouteB(val id: String) : NavKey


private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(RouteA::class, RouteA.serializer())
            subclass(RouteB::class, RouteB.serializer())

        }
    }
}

@Composable
fun BasicActivity() {
    val backStack = rememberNavBackStack(config, RouteB("23"))
    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLast()
        },
        entryProvider = entryProvider {
            entry<RouteB> {
                ContentOrange("This is Screen B") {
                    Button(onClick = {
                        backStack.add(RouteA)
                    }) {
                        Text("Screen B")
                    }
                }

            }
            entry<RouteA> {
                ContentRed("This is ScreenA") {
                    Button(onClick = {
                        backStack.removeLastOrNull()
                    }){
                        Text("ScreenA")
                    }
                }
            }
        }
    )
}