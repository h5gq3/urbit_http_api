import urbit.http.api.GraphStore
import urbit.http.api.Urbit

val zod = Urbit(code = "lidlut-tabwed-pillex-ridrup", url = "http://localhost:8080")
val graphstore = GraphStore(zod)

fun main() {
    zod.connect()
    println(zod.ship)
    zod.poke("zod","hood","helm-hi","openinggg airlock")
    zod.scry("file-server", "/clay/base/hash", "json")
    zod.spider("graph-view-action", "json", "graph-create", """[{"foo": "bar"}]""")
    zod.subscribe("zod","chat-view","/primary")
    zod.sseInit()
    zod.unsubscribe(2)
    zod.delete()
    graphstore.createUnmanagedGraph("myfirstgraph", "myfirstgraph", "", "open", "link")

}