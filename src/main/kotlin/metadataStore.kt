package urbit.http.api

import java.util.*

fun msToDa(ms: Long, mil: Long? = null): String {
    val d = Calendar.getInstance()
    d.setTimeInMillis(ms)
    fun fil(n: Int): String {
        return if (n >= 10) "$n" else "0"+n
    }
    return "~${d.get(1)}."+"${d.get(2)+1}."+"${fil(d.get(5))}.."+"${fil(d.get(11))}."+"${fil(d.get(12))}."+"${fil(d.get(13))}"+"${if (mil != null) "..0000" else ""}"
}

class MetadataStore(val instance: Urbit) {

    fun addMetadata(appName: String, appPath: String, groupPath: String, title: String, description: String, dateCreated: String, color: String, moduleName: String) {
        instance.addMetadata(appName,appPath,groupPath,title,description,dateCreated,color,moduleName)
    }

    fun updateMetadata(appName: String, appPath: String, groupPath: String, title: String?, description: String? = null, dateCreated: String? = null, color: String? = null, moduleName: String? = null) {
        instance.updateMetadata(appName,appPath,groupPath,title,description,dateCreated,color,moduleName)
    }

    fun Urbit.addMetadata(appName: String, appPath: String, groupPath: String, title: String, description: String, dateCreated: String, color: String, moduleName: String) {
        poke(ship!!.drop(1), "metadata-hook", "metadata-action", """{"add":{"group-path":"$groupPath","resource":{"app-path":"$appPath","app-name":"$appName"},"metadata":{"title":"$title","description":"$description","color":"$color","date-created":"$dateCreated","creator":"$ship","module":"$moduleName"}}}""")
    }

    fun Urbit.updateMetadata(appName: String, appPath: String, groupPath: String, title: String?, description: String?, dateCreated: String?, color: String?, moduleName: String?) {
        poke(ship!!.drop(1), "metadata-hook", "metadata-action", """{"add":{"group-path":"$groupPath","resource":{"app-path":"$appPath","app-name":"$appName"},"metadata":{"title":"$title","description":"$description","color":"$color","date-created":"$dateCreated","creator":"$ship","module":"$moduleName"}}}""")
    }
}

