package urbit.http.api

class GroupStore(val instance: Urbit) {

    fun addMembers(ship: String, name: String, ships: String) {
        instance.addMembers(ship,name,ships)
    }

    fun removeMembers(ship: String, name: String, ships: Array<String>) {
        instance.removeMembers(ship,name,ships)
    }

    fun addTag(ship: String, name: String, tag: String, ships: Array<String>) {
        instance.addTag(ship,name,tag,ships)
    }

    fun removeTag(ship: String, name: String, tag: String, ships: Array<String>) {
        instance.removeTag(ship,name,tag,ships)
    }

    fun removeGroup(ship: String, name: String) {
        instance.removeGroup(ship,name)
    }

    fun changePolicy(ship: String, name: String, diff: String) {
        instance.changePolicy(ship,name,diff)
    }

    fun Urbit.addMembers(ship: String, name: String, ships: String) {
        val resource = makeResource(ship, name)
        poke(ship!!.drop(1),"group-push-hook","group-update","""{"addMembers":{"resource":$resource,"ships":$ships}}""")
    }

    fun Urbit.removeMembers(ship: String, name: String, ships: Array<String>) {
        val resource = makeResource(ship, name)
        poke(ship!!.drop(1),"group-push-hook","group-update","""{"removeMembers":$resource,$ships}""")
    }

    fun Urbit.addTag(ship: String, name: String, tag: String, ships: Array<String>) {
        val resource = makeResource(ship, name)
        poke(ship!!.drop(1),"group-push-hook","group-update","""{"addTag":$resource,$tag,$ships}""")
    }

    fun Urbit.removeTag(ship: String, name: String, tag: String, ships: Array<String>) {
        val resource = makeResource(ship, name)
        poke(ship!!.drop(1),"group-push-hook","group-update","""{"removeTag":$resource,$tag,$ships}""")
    }

    fun Urbit.removeGroup(ship: String, name: String) {
        val resource = makeResource(ship, name)
        poke(ship!!.drop(1),"group-store","group-update","""{"removeGroup":$resource}""")
    }

    fun Urbit.changePolicy(ship: String, name: String, diff: String) {
        val resource = makeResource(ship, name)
        poke(ship!!.drop(1),"group-push-hook","group-update","""{"changePolicy":$resource,$diff}""")
    }
}

