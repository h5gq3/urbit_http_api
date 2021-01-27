import urbit.http.api.GraphStore
import urbit.http.api.GroupStore
import urbit.http.api.MetadataStore
import urbit.http.api.Urbit

val zod = Urbit(code = "lidlut-tabwed-pillex-ridrup", url = "http://192.168.4.133:8080")
val graphstore = GraphStore(zod)
val groupstore = GroupStore(zod)
val metadatastore = MetadataStore(zod)

fun main() {
//    // connect to your urbit instance
//    zod.connect()
//    // "poke" an app on your urbit with marked (type validated) data: poke app "hood" with a text string marked as "helm-hi"
//    zod.poke("zod","hood","helm-hi",""""opening airlock"""")
//    // "scry" a path of an app on your urbit and return back marked data (usually json)
//    zod.scry("hood", "/date", "json")
//    // initialize SSE bridge to receive events from your urbit
//    zod.sseInit()
//    // "spider" (run) a thread on your urbit with marked data and return marked data (usually json)
//    zod.spider("graph-view-action", "json", "graph-create", """[{"foo": "bar"}]""")
//    // subscribe to a path on an app to receive events
//    zod.subscribe("zod","weather","/all")
//    // unsubscribe a path on an app to stop receiving events
//    zod.unsubscribe(2)
//    // delete a channel, probably unnecessary as urbit cleans up dead channels after inactivity
//    zod.delete()
//
//    // G r a p h S t o r e
//
//    // create an empty graph with open policy (can be joined by anyone), marked as "link" (appears as a collection on "Collections" app on Landscape)
//    graphstore.createUnmanagedGraph("test-graph3", "mygraph", "testing", """{"policy":{"open":{"banRanks":[],"banned":[]}}}""", "link")
//    // create an empty graph with open policy (can be joined by anyone), marked as "chat" (appears as a chat on Landscape)
//    graphstore.createUnmanagedGraph("test-graph3", "mygraph", "testing", """{"policy":{"open":{"banRanks":[],"banned":[]}}}""", "chat")
//    // add a graph to previously created empty graph
//    graphstore.addGraph("~zod", "dm--balpub-sorlyt-solpun-hacnum--bolrum-tinten-widsyx-marzod",""""1":{"post":{"author":"${zod.ship}","index":"/${Calendar.getInstance().timeInMillis}","time-sent":${zod.uid},"signatures":[],"hash":null,"contents":[{"text":"ostaneeduisud"},{"url":"www.tere.ee"}]},"children":{"empty":null}}""", "link",true)
//    // add a post to previously created empty graph
//    graphstore.addPost("~zod", "test-graph3",""""author":"${zod.ship}","index":"/${Calendar.getInstance().timeInMillis}","time-sent":${zod.uid},"hash":null,"signatures":[],"contents":[{"text":"ostaneeduisud"},{"url":"www.tere.ee"}]""")
//    // delete a graph
//    graphstore.deleteGraph("test-graph")
//
//    // G r o u p S t o r e
//
//    // add members to a group
//    groupstore.addMembers("~zod","testgroup","""["~bud","~wet","~sun"]""")
//    // remove members from a group
//    groupstore.removeMembers("~zod","drain gang", arrayOf("~bud"))
//
//    // M e t a d a t a S t o r e
//
//    // add metadata to your urbit's home tile (contact app on path /home)
//    metadatastore.addMetadata("contact","/home","/home","DMs + Drafts","","","0x0","")
//    // edit metadata of your urbit's home tile (contact app on path /home): rename tile to "Mailbox"
//    metadatastore.updateMetadata("contacts", "/ship/~zod/home", "/ship/~zod/home", "DG", "", dateCreated = msToDa(Calendar.getInstance().timeInMillis), "ff0000", "")
}