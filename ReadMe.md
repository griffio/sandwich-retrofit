# Basic Setup

Example Retrofit API with Moshi Json for Kotlin consumer

The library [Sandwich](https://github.com/skydoves/sandwich) adapts the Retrofit network response into a Kotlin
combinator

Optionally with suspend coroutine support

```
./gradlew run
```

```
Repo(name=boysenberry-repo-1, url=https://api.github.com/repos/octocat/boysenberry-repo-1, createdAt=2018-05-10T17:51:29)
Repo(name=git-consortium, url=https://api.github.com/repos/octocat/git-consortium, createdAt=2014-03-28T17:55:38)
Repo(name=hello-worId, url=https://api.github.com/repos/octocat/hello-worId, createdAt=2014-06-18T21:26:19)
Repo(name=Hello-World, url=https://api.github.com/repos/octocat/Hello-World, createdAt=2011-01-26T19:01:12)
Repo(name=linguist, url=https://api.github.com/repos/octocat/linguist, createdAt=2016-08-02T17:35:14)
Repo(name=octocat.github.io, url=https://api.github.com/repos/octocat/octocat.github.io, createdAt=2014-03-18T20:54:39)
Repo(name=Spoon-Knife, url=https://api.github.com/repos/octocat/Spoon-Knife, createdAt=2011-01-27T19:30:43)
Repo(name=test-repo1, url=https://api.github.com/repos/octocat/test-repo1, createdAt=2016-04-14T21:29:25)
{"message":"Not Found","documentation_url":"https://docs.github.com/rest/reference/repos#list-repositories-for-a-user"}
```

### Square Libraries

OkHttp https://square.github.io/okhttp/

Retrofit https://square.github.io/retrofit/

Moshi https://github.com/square/moshi
