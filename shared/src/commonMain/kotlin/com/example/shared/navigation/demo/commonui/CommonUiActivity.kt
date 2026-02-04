package com.example.shared.navigation.demo.commonui

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.shared.navigation.demo.content.ContentBlue
import com.example.shared.navigation.demo.content.ContentGreen
import com.example.shared.navigation.demo.content.ContentPurple
import com.example.shared.navigation.demo.content.ContentRed
import kmp_library.shared.generated.resources.Res
import kmp_library.shared.generated.resources.face
import kmp_library.shared.generated.resources.home
import kmp_library.shared.generated.resources.play_arrow
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource


sealed interface TopLevelRoute {
    val icon: DrawableResource
}


private data object HomeRoute : TopLevelRoute {
    override val icon = Res.drawable.home
}

private data object ChatList : TopLevelRoute {
    override val icon = Res.drawable.face
}

private data object ChatDetail
private data object Camera : TopLevelRoute {
    override val icon = Res.drawable.play_arrow
}





class TopLevelBackStack<T: Any>(startKey:T){
    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )


    var topLevelKey by mutableStateOf(startKey)

    val backStack = mutableStateListOf(startKey)
    private fun updateBackStack() =
        backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }

    fun addTopLevel(key: T) {

        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null) {
            topLevelStacks.put(key, mutableStateListOf(key))
        } else {
            // Otherwise just move it to the end of the stacks
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}
private val TOP_LEVEL_ROUTE = listOf(HomeRoute,ChatList, Camera)

@Composable
fun CommonUiActivity() {
    val topLevelRouteStack = remember {
        TopLevelBackStack<Any>(HomeRoute)
    }


    Scaffold(
        bottomBar = {
            NavigationBar {
                TOP_LEVEL_ROUTE.forEach { topLevelRoute ->

                    val isSelected = topLevelRoute == topLevelRouteStack.topLevelKey
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            topLevelRouteStack.addTopLevel(topLevelRoute)
                        },
                        icon = {
                            Icon(
                                imageVector = vectorResource(topLevelRoute.icon),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    ){
        _->
        NavDisplay(
            backStack = topLevelRouteStack.backStack,
            onBack = {topLevelRouteStack.removeLast()},
            entryProvider = entryProvider {
                entry<HomeRoute> {
                    ContentRed("Home screen")
                }

                entry<ChatList> {
                    ContentGreen("Chat list screen"){
                        Button(onClick = {
                            topLevelRouteStack.add(ChatDetail)
                        }){
                            Text("Go to converstaion")
                        }
                    }
                }
                entry<ChatDetail> {
                    ContentBlue("Chat detail screen")
                }
                entry<Camera> {
                    ContentPurple("Camera screen")
                }
            }
        )
    }
}