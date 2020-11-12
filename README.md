# Urbit HTTP API

Urbit HTTP API library in Kotlin for connecting to an Urbit instance

## Usage

1. Copy `urbit.kt` in `/src/main/kotlin/urbit.kt` to your Kotlin project
2. Add following dependencies to your project `build.gradle.kts`
```
implementation("com.squareup.okhttp3:okhttp:4.9.0")
implementation("com.squareup.okhttp3:okhttp-sse:4.9.0")
```
3. Import the `Urbit` class in your project with top-level statement `import urbit.http.api.Urbit`
4. Initialize the `Urbit` class. E.g `val zod = Urbit(code, url)`
5. Call methods in your `main` function block. E.g `zod.connect()`

## Example

```
import urbit.http.api.Urbit

val zod = Urbit(code = "lidlut-tabwed-pillex-ridrup", url = "http://localhost:8080")

fun main() {
    zod.connect()
    zod.poke("zod","hood","helm-hi","openinggg airlock")
    zod.subscribe("zod","chat-view","/primary")
    zod.sseInit()
    zod.unsubscribe(2)
    zod.delete()
}
```

## To do
* improve documentation
* write methods for scry, spider
* handle timeouts better
* abstract away message boilerplate, let all actions call sendmessage()
* restructure code into Kotlin Multiplatform
