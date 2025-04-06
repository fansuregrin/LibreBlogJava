# LibreBlog

LibreBlog is a blogging platform powered by the Spring framework for its backend API.

## API Endpoints
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

## Setup, Build, and Run
## Acknowledgements