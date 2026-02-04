package com.example.shared.navigation.demo.animated

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.shared.navigation.demo.content.ContentMauve
import com.example.shared.navigation.demo.content.ContentOrange
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


@Serializable
private data object ScreenA : NavKey {
}

@Serializable
private data object ScreenB : NavKey

@Serializable
private object ScreenC : NavKey


private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(ScreenA::class, ScreenA.serializer())
            subclass(ScreenB::class, ScreenB.serializer())
            subclass(ScreenC::class, ScreenC.serializer())
        }
    }
}

@Composable
fun AnimatedActivity() {
    val backStack = rememberNavBackStack(config, ScreenA)
    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        //前进导航 对应的是add操作
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(1000)
            ).togetherWith(
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(1000)
                )
            )
        },
        //返回 返回键/手势
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(1000)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(1000)
            )
        },
        //android 13 边缘滑动
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(1000)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(1000)
            )
        },
        entryProvider =
            entryProvider {

                entry(ScreenA) {
                    ContentOrange("this is ScreenA") {
                        Button(onClick = {
                            backStack.add(ScreenB)
                        }) {
                            Text("go to ScreenB")
                        }
                    }
                }
                entry<ScreenB> {
                    ContentMauve("This is Screen B") {
                        Button(onClick = { backStack.add(ScreenC) }) {
                            Text("Go to Screen C")
                        }
                    }
                }
                entry<ScreenC> {
                    ContentOrange("This is Screen C") {}
                }


            }
    )
}