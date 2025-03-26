# LibreBlog

LibreBlog is a blogging platform powered by the Spring framework for its backend API.

## API Endpoints
- Article
  - GET `/article/list`
  - GET `/article/me/list`
  - GET `/article/{id}`
  - POST `/article`
  - PUT `/article`
  - DELETE `/article`
- User
  - POST `/user/login`
  - POST `/user/register`
  - GET `/user/list`
  - GET `/user/me`
  - PATCH `/user/me`
  - PATCH `/user/me/password`
  - GET `/user/{id}`
  - PATCH `/user`
  - PATCH `/user/password`
  - POST `/user`
  - DELETE `/user`
- Category
  - GET `/category/all`
  - GET `/category/me/list`
  - GET `/category/id/{id}`
  - GET `/category/slug/{slug}`
  - POST `/category`
  - PUT `/category`
  - DELETE `/category`
- Tag
  - GET `/tag/all`
  - GET `/tag/me/list`
  - GET `/tag/id/{id}`
  - GET `/tag/slug/{slug}`
  - POST `/tag`
  - PUT `/tag`
  - DELETE `/tag`
- Role
- Menu

## Setup, Build, and Run
## Acknowledgements