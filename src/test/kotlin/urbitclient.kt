
import urbit.http.api.GraphStore
import urbit.http.api.Urbit
import java.util.*

val zod = Urbit(code = "lidlut-tabwed-pillex-ridrup", url = "http://localhost:8080")
val graphstore = GraphStore(zod)

fun main() {
    // connect to your urbit instance
    zod.connect()
    // initialize SSE bridge to receive events from your urbit
    zod.sseInit()
    // "poke" an app on your urbit with marked (type validated) data: poke app "hood" with a text string marked as "helm-hi"
    zod.poke("zod","hood","helm-hi",""""openinggg airlock"""")
    // "scry" a path of an app on your urbit and return back marked data (usually json)
    zod.scry("file-server", "/clay/base/hash", "json")
    // "spider" (run) a thread on your urbit with marked data and return marked data (usually json)
    zod.spider("graph-view-action", "json", "graph-create", """[{"foo": "bar"}]""")
    // subscribe to a path on an app to receive events
    zod.subscribe("zod","weather","/all")
    // unsubscribe a path on an app to stop receiving events
    zod.unsubscribe(2)
    // delete a channel, probably unnecessary as urbit cleans up dead channels after inactivity
    zod.delete()
    // create an empty graph with open policy (can be joined by anyone), marked as "link" (appears as a collection on "Collections" app on Landscape)
    graphstore.createUnmanagedGraph("test-graph3", "mygraph", "testing", """{"policy":{"open":{"banRanks":[],"banned":[]}}}""", "link")
    // create an empty graph with open policy (can be joined by anyone), marked as "chat" (appears as a chat on Landscape)
    graphstore.createUnmanagedGraph("test-graph3", "mygraph", "testing", """{"policy":{"open":{"banRanks":[],"banned":[]}}}""", "chat")
    // add a graph to previously created empty graph
    graphstore.addGraph("~zod", "dm--balpub-sorlyt-solpun-hacnum--bolrum-tinten-widsyx-marzod",""""1":{"post":{"author":"${zod.ship}","index":"/${Calendar.getInstance().timeInMillis}","time-sent":${zod.uid},"signatures":[],"hash":null,"contents":[{"text":"ostaneeduisud"},{"url":"www.tere.ee"}]},"children":{"empty":null}}""", "link",true)
    // add a post to previously created empty graph
    graphstore.addPost("~zod", "test-graph3",""""author":"${zod.ship}","index":"/${Calendar.getInstance().timeInMillis}","time-sent":${zod.uid},"hash":null,"signatures":[],"contents":[{"text":"ostaneeduisud"},{"url":"www.tere.ee"}]""")
    // delete a graph
    graphstore.deleteGraph("test-graph")
}