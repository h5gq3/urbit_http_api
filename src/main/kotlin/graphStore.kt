package urbit.http.api

import java.util.*


fun numToUd(num: Int): String {
    return num.toString()
            .map { it.toString() }
            .reversed().chunked(3)
            .map { it.reversed() }
            .reversed()
            .map { it.joinToString(separator = "") }
            .joinToString(separator = ".")
}

fun resourceFromPath(path: String): String {
    val (ship: String, name: String) = path.split("/").drop(2)
    return """{"ship":"$ship","name":"$name"}"""
}

fun makeResource(ship: String?, name: String): String {
    return """{"ship":"$ship","name":"$name"}"""
}

fun moduleToMark(mod: String): Any? {
    when (mod) {
        "link" -> return "graph-validator-link"
        "publish" -> return "graph-validator-publish"
        else -> return null
    }
}

class GraphStore(val instance: Urbit) {

    fun createManagedGraph(name: String, title: String, description: String, group: String, mod: String) {
        instance.createManagedGraph(name, title, description, group, mod)
    }

    fun createUnmanagedGraph(name: String, title: String, description: String, policy: String, mod: String) {
        instance.createUnmanagedGraph(name, title, description, policy, mod)
    }

    fun joinGraph(ship: String, name: String) {
        instance.joinGraph(ship, name)
    }

    fun deleteGraph(name: String) {
        instance.deleteGraph(name)
    }

    fun leaveGraph(ship: String, name: String) {
        instance.leaveGraph(ship, name)
    }

    fun groupifyGraph(ship: String, name: String, toPath: String) {
        instance.groupifyGraph(ship, name, toPath)
    }

    fun eval(cord: String) {
        instance.eval(cord)
    }

    fun addGraph(ship: String, name: String, graph: Any, mark: Any, overwrite: Boolean) {
        instance.addGraph(ship, name, graph, mark, overwrite)
    }

    fun addPost(ship: String, name: String, post: String) {
        instance.addPost(ship, name, post)
    }

    fun addNode(ship: String, name: String, node: Node) {
        instance.addNode(ship, name, node)
    }

    fun addNodes(ship: String, name: String, nodes: String) {
        instance.addNodes(ship, name, nodes)
    }

    fun removeNodes(ship: String, name: String, indices: List<String>) {
        instance.removeNodes(ship, name, indices)
    }

    fun getKeys() {
        instance.getKeys()
    }

    fun getTags() {
        instance.getTags()
    }

    fun getTagQueries() {
        instance.getTagQueries()
    }

    fun getGraph(ship: String, resource: String) {
        instance.getGraph(ship, resource)
    }

    fun getGraphSubset(ship: String, resource: String, start: String, end: String) {
        instance.getGraphSubset(ship, resource, start, end)
    }

    fun getNode(ship: String, resource: String, index: String) {
        instance.getNode(ship, resource, index)
    }

    fun Urbit.createManagedGraph(name: String, title: String, description: String, group: String, mod: String) {
        val associated = """{"group":"${resourceFromPath(group)}"}"""
        val resource = makeResource(ship, name)

        spider("graph-view-action", "json", "graph-create", """{"create":{"resource":$resource,"title":"$title","description":"$description","associated":$associated,"module":"$mod","mark":"${moduleToMark(mod)}"}}""")
    }


    fun Urbit.createUnmanagedGraph(name: String, title: String, description: String, policy: String, mod: String) {
        val resource = makeResource(ship, name)

        spider("graph-view-action", "json", "graph-create", """{"create":{"resource":${makeResource(ship, name)},"title":"$title","description":"$description","mark":"${moduleToMark(mod)}","associated":{"policy":{"open":{"banRanks":[],"banned":[]}}},"module":"$mod"}}""")
    }

    fun Urbit.joinGraph(ship: String, name: String) {
        val resource = makeResource(ship, name)

        spider("graph-view-action", "json", "graph-join", """{"join":{"resource":$resource,"ship":"$ship"}}""")
    }

    fun Urbit.deleteGraph(name: String) {
        val resource = makeResource(ship, name)

        spider("graph-view-action", "json", "graph-delete", """{"delete":{"resource":$resource}}""")
    }

    fun Urbit.leaveGraph(ship: String, name: String) {
        val resource = makeResource(ship, name)

        spider("graph-view-action", "json", "graph-leave", """{"leave":{"resource":$resource}}""")
    }

    fun Urbit.groupifyGraph(ship: String, name: String, toPath: String) {
        val resource = makeResource(ship, name)

        spider("graph-view-action", "json", "graph-groupify", """{"groupify":{"resource":$resource,"to":"$toPath"}}""")
    }

    fun Urbit.eval(cord: String) {

        spider("graph-view-action", "tang", "graph-eval", """{"eval": "$cord"}""")
    }

    fun Urbit.addGraph(ship: String, name: String, graph: Any, mark: Any, overwrite: Boolean) {
        val resource = makeResource(ship, name)

        poke(ship.drop(1), "graph-store", "graph-update", """{"add-graph":{"resource":$resource,"graph":{$graph},"mark":"$mark","overwrite":$overwrite}}""")
    }

    fun Urbit.addPost(ship: String, name: String, post: String) {
        val resource = makeResource(ship, name)
        val nodes = """"/${Calendar.getInstance().timeInMillis}":{"post":{$post},"children":null}"""

        poke(ship.drop(1), "graph-push-hook", "graph-update", """{"add-nodes":{"resource":$resource,"nodes":{$nodes}}}""")
    }

    fun Urbit.addNode(ship: String, name: String, node: Node) {
        val resource = makeResource(ship, name)
        val nodes = """{"${node.post.index}":"$node"}"""

        poke(ship.drop(1), "graph-push-hook", "graph-update", """{"add-nodes":{"resource":$resource,"nodes":{$nodes}}}""")
    }

    fun Urbit.addNodes(ship: String, name: String, nodes: String) {
        val resource = makeResource(ship, name)

        poke(ship.drop(1), "graph-push-hook", "graph-update", """{"add-nodes":{"resource":$resource,"nodes":{$nodes}}}""")
    }

    fun Urbit.removeNodes(ship: String, name: String, indices: List<String>) {
        val resource = makeResource(ship, name)

        poke(ship, "graph-push-hook", "graph-update", """{"remove-nodes":{"resource":$resource,"indices":$indices}}""")
    }

    fun Urbit.getKeys() {
        scry("graph-store", "/keys", "json")
    }

    fun Urbit.getTags() {
        scry("graph-store", "/tags", "json")
    }

    fun Urbit.getTagQueries() {
        scry("graph-store", "/tag-queries", "json")
    }

    fun Urbit.getGraph(ship: String, resource: String) {
        scry("graph-store", "/graph/$ship/$resource", "json")
    }

    fun Urbit.getGraphSubset(ship: String, resource: String, start: String, end: String) {
        scry("graph-store", "/graph-subset/$ship/$resource/$end/$start", "json")
    }

    fun Urbit.getNode(ship: String, resource: String, index: String) {
        // TODO: 06.12.2020  check the format of index: does it start with a slash?
        val idx = index.split("/").map { numToUd(it.toInt()) }.joinToString(separator = "/")
        scry("graph-store", "/node/$ship/$resource/${idx}", "json")
    }

}

interface Post {
    val author: String
    val contents: String
    val hash: String?
    val index: String
    val pending: Boolean
    val signatures: MutableList<String>
    val `time-sent`: Int
}

interface Node {
    val post: Post
    val children: String
}