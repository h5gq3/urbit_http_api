package urbit.http.api

import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import okio.IOException
import java.util.*
import java.util.concurrent.TimeUnit


class Urbit(val code: String, val url: String) {
    val client = OkHttpClient.Builder().readTimeout(0, TimeUnit.SECONDS).build()
    val sseclient = EventSources.createFactory(client)
    var cookie: String? = null
    val uid: Long = Calendar.getInstance().timeInMillis
    var lastEventId: Int = 0
    val channelUrl: String = "$url/~/channel/$uid"

    fun getEventId(): Int {
        lastEventId = lastEventId + 1
        return lastEventId
    }

    fun connect() {
        val formBody = FormBody.Builder()
            .add("password", code)
            .build()
        val request = Request.Builder()
            .url("$url/~/login")
            .post(formBody)
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            cookie = response.header("set-cookie")
            cookie = cookie?.split(";")?.get(0)
        }
    }


    fun poke(ship: String, app: String, mark: String, j: String) {
        val putBody = """[{"id":${getEventId()},"action":"poke","ship":"$ship","app":"$app","mark":"$mark","json":"$j"}]"""
        var request = Request.Builder()
            .url(channelUrl)
            .header("Cookie", cookie!!)
            .put(putBody.toRequestBody("application/json".toMediaType()))
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

        }
    }

    fun subscribe(ship: String, app: String, path: String) {
        val putBody = """[{"id":${getEventId()},"action":"subscribe","ship":"$ship","app":"$app","path":"$path"}]"""
        var request = Request.Builder()
            .url(channelUrl)
            .header("Cookie", cookie!!)
            .put(putBody.toRequestBody("application/json".toMediaType()))
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

        }
    }

    fun unsubscribe(subscription: Int) {
        val putBody = """[{"id":${getEventId()},"action":"unsubscribe","subscription":$subscription}]"""
        var request = Request.Builder()
            .url(channelUrl)
            .header("Cookie", cookie!!)
            .put(putBody.toRequestBody("application/json".toMediaType()))
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            println("unsubscribed $subscription")

        }
    }

    fun delete() {
        val putBody = """[{"id":${getEventId()},"action":"delete"}]"""
        var request = Request.Builder()
            .url(channelUrl)
            .header("Cookie", cookie!!)
            .put(putBody.toRequestBody("application/json".toMediaType()))
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            println("deleted channel")

        }
        //sseInit().cancel()
        //client.dispatcher.executorService.shutdown()
    }

    fun ack(eventId: String) {
        val body = """[{"id":${getEventId()},"action":"ack","event-id":${eventId.toInt()}}]"""
        val request = Request.Builder()
            .url(channelUrl)
            .header("Cookie", cookie!!)
            .put(body.toRequestBody("application/json".toMediaType()))
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
        }
        println("ack sent")
    }

    fun sseInit(): EventSource {
        val request = Request.Builder()
            .url(channelUrl)
            .header("Cookie", cookie!!)
            .header("Connection", "keep-alive")
            .build()
        return sseclient.newEventSource(request = request, listener = Sselistener())
    }

    inner class Sselistener : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            println("sse connection opened")
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            println("something failed")
            t?.printStackTrace()

        }

        override fun onClosed(eventSource: EventSource) {
            println("sse connection closed")

        }

        override fun onEvent(eventSource: EventSource, id: String?, type: String?, data: String) {
            ack(eventId = id!!)
            println(data)
            println("event received")
        }
    }
}