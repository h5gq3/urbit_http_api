import urbit.http.api.Urbit

val zod = Urbit(code = "lidlut-tabwed-pillex-ridrup", url = "http://localhost:8080")

fun main() {
    zod.connect()
    zod.poke("zod","hood","helm-hi","openinggg airlock")
    zod.subscribe("zod","chat-view","/primary")
    zod.sseInit()
    //zod.unsubscribe(2)
    //zod.delete()
}