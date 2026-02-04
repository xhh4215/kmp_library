package com.example.shared.navigation.demo.multiplestacks

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.shared.navigation.demo.content.ContentGreen
import com.example.shared.navigation.demo.content.ContentMauve
import com.example.shared.navigation.demo.content.ContentOrange
import com.example.shared.navigation.demo.content.ContentPink
import com.example.shared.navigation.demo.content.ContentPurple
import com.example.shared.navigation.demo.content.ContentRed


fun EntryProviderScope<NavKey>.featureASection(
    onSubRouteClick:()-> Unit
){
    entry<RouteA> {
        ContentRed("Route A") {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = onSubRouteClick) {
                    Text("Go to A1")
                }
            }
        }
    }
    entry<RouteA1> {
        ContentPink("Route A1") {
            var count by rememberSaveable {
                mutableIntStateOf(0)
            }

            Button(onClick = { count++ }) {
                Text("Value: $count")
            }
        }
    }
}

fun EntryProviderScope<NavKey>.featureBSection(
    onSubRouteClick: () -> Unit,
) {
    entry<RouteB> {
        ContentGreen("Route B") {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = onSubRouteClick) {
                    Text("Go to B1")
                }
            }
        }
    }
    entry<RouteB1> {
        ContentPurple("Route B1") {
            var count by rememberSaveable {
                mutableIntStateOf(0)
            }
            Button(onClick = { count++ }) {
                Text("Value: $count")
            }
        }
    }
}

fun EntryProviderScope<NavKey>.featureCSection(
    onSubRouteClick: () -> Unit,
) {
    entry<RouteC> {
        ContentMauve("Route C") {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = onSubRouteClick) {
                    Text("Go to C1")
                }
            }
        }
    }
    entry<RouteC1> {
        ContentOrange("Route C1") {
            var count by rememberSaveable {
                mutableIntStateOf(0)
            }

            Button(onClick = { count++ }) {
                Text("Value: $count")
            }
        }
    }
}