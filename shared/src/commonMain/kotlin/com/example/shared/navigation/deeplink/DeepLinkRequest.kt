package com.example.shared.navigation.deeplink


data class DeepLinkRequest(
    val uri: String,
    val query: Map<String, String> = emptyMap()
)

fun Intent.toDeepLinkRequest(): DeepLinkRequest? {
    val uri = data?.toString() ?: return null
    val query = data?.queryParameterNames
        ?.associateWith { data?.getQueryParameter(it).orEmpty() }
        .orEmpty()

    return DeepLinkRequest(uri, query)
}

