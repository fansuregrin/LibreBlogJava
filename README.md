# LibreBlog

LibreBlog is a blogging platform powered by the Spring framework for its backend API.

<details>
<summary>See api endpoints</summary>

- Article
  - GET `/articles/list`
  - GET `/articles/{id}`
  - GET `/admin/articles/list`
  - POST `/admin/articles`
  - PUT `/admin/articles`
  - DELETE `/admin/articles`
- User
  - POST `/login`
  - POST `/register`
  - GET `/logout`
  - GET `/users/{id}`
  - GET `/admin/users/list`
  - GET `/admin/users/me`
  - PATCH `/admin/users/me`
  - PATCH `/admin/users/me/password`
  - PATCH `/admin/users`
  - PATCH `/admin/users/password`
  - POST `/admin/users`
  - DELETE `/admin/users`
- Category
  - GET `/categories/all`
  - GET `/categories/id/{id}`
  - GET `/categories/slug/{slug}`
  - GET `/admin/categories/list`
  - POST `/admin/categories`
  - PUT `/admin/categories`
  - DELETE `/admin/categories`
- Tag
  - GET `/tags/id/{id}`
  - GET `/tags/slug/{slug}`
  - GET `/admin/tags/list`
  - POST `/admin/tags`
  - PUT `/admin/tags`
  - DELETE `/admin/tags`
- Role
  - GET `/admin/roles/list`
- Menu
  - GET `/admin/menus`
  - GET `/admin/menus/me`
</details>

## Setup, Build, and Run

### Setup
1. Mysql
   - Run [libreblog.sql](./sql/libreblog.sql) in mysql shell.
   - Update the MySQL configuration to yours under `spring.datasource` in [application.yaml](./src/main/resources/application.yaml).
2. Redis
   - Update the Redis configuration to yours under `spring.data.redis` in [application.yaml](./src/main/resources/application.yaml).
3. OSS
   - Aliyun OSS: Update the configuration to yours under `oss.aliyun` in [application.yaml](./src/main/resources/application.yaml).
4. JDK 17+
5. Maven

### Build
```shell
mvn package
```
or skip tests:
```shell
mvn package -DskipTests
```

### Run
1.On Windows:
```powershell
$env:ALIYUN_OSS_ACCESS_KEY_ID = "YOURS"
$env:ALIYUN_OSS_ACCESS_KEY_SECRET = "YOURS"
java -jar <the-jar-file>
```

2.On Linux
```shell
export ALIYUN_OSS_ACCESS_KEY_ID="YOURS"
export ALIYUN_OSS_ACCESS_KEY_SECRET="YOURS"
java -jar <the-jar-file>
```

## Acknowledgements
- IntelliJ IDEA
- ApiPost