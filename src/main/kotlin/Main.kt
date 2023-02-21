package griffio

import com.skydoves.sandwich.*
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface GitHubService {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): ApiResponse<List<Repo>> // sandwich ApiResponse
}

data class Repo(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
    @Json(name = "created_at") val createdAt: LocalDateTime,
)

data class ErrorMsg(
    @Json(name = "message") val message: String,
    @Json(name = "documentation_url") val url: String,
)

class LocalDateAdapter(private val formatter: DateTimeFormatter) {
    @ToJson
    fun toJson(dateTime: LocalDateTime?) =
        dateTime?.let { formatter.format(dateTime) }

    @FromJson
    fun fromJson(dateString: String?) =
        dateString?.let { LocalDateTime.parse(it, formatter) }
}

val moshi: Moshi = Moshi.Builder()
    .add(LocalDateAdapter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
    .add(KotlinJsonAdapterFactory())
    .build()

// https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/
val http = OkHttpClient.Builder().build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .client(http)
    .addCallAdapterFactory(ApiResponseCallAdapterFactory.create()) // IO Dispatcher
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

val service: GitHubService = retrofit.create() // retrofit KotlinExtensions

// example extension method for retrofit see sandwich
// https://github.com/skydoves/sandwich/blob/main/sandwich-serialization/src/main/kotlin/com/skydoves/sandwich/serialization/SerializationExtensions.kt
inline fun <reified T> ApiResponse<T>.deserializeErrorBody(): T? {
    return if (this is ApiResponse.Failure.Error<T>) {
        val converter = retrofit.responseBodyConverter<T>(T::class.java, arrayOfNulls(0))
        val errorBody = errorBody ?: error("no error body")
        converter.convert(errorBody) ?: error("failed to convert error body")
    } else null
}

// sandwich model mapper for error responses
val errorMapper = ApiErrorModelMapper { apiErrorResponse ->
    apiErrorResponse.deserializeErrorBody()
}

suspend fun main() {

    val octocat = service.listRepos("octocat")
    // handle 200 ApiResponse
    octocat.onSuccess { println(data.joinToString("\n")) }

    val bogus = service.listRepos("octocat?")
    // handle 404 ApiResponse
    bogus.onError {
        println(map(errorMapper))
    }

    // okHttp non-daemon thread pool needs to be stopped
    http.dispatcher.executorService.shutdown()
    http.connectionPool.evictAll()
}
