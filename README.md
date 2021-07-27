# **Scooter Sharing Service**
![img_1.png](img_1.png)

Requests:
- [AUTH requests](#auth-requests)
    - [Login](#login)
    - [Logout](#logout)
- [USER requests](#user-requests)
    - [Create user](#create-user)
    - [Get page of all users](#get-page-of-all-users)
    - [Get user by id](#get-user-by-id)
    - [Delete user by id](#gelete-user-by-id)

## AUTH requests
- ### Login:

> `POST -> "/auth/login"`

>_Request parameters:_ `NONE`

>_Request body:_
```java
{
    "password" : "12345678",
    "login" : "super.admin@scooter.com"
}
```
>Authenticated: `NONE`


- ### Logout:

> `PUT -> "/auth/logout"`

>_Request parameters:_ `NONE`

>_Request body:_
```java
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdXBlci5hZG1pbkBzY29vdGVyLmNvbSIsImV4cCI6MTYyNzM4NzAyMX0.HnViKzhdiv8ek0PzrK35KiKSZB9C5XRbRvHZlVdZHVdDSsSrAx4PlhZxP5Wc6RsWv3T7pJJFcWqtuiWnXPbOHQ"
}
```
>Authenticated: `NONE`


## USER requests
- ### Create user
> `POST -> "/users"`

>_Request parameters:_ `NONE`

>_Request parameters:_ `NONE`

>_Request body:_
```java
{
    "firstname" : "Kiryl",
    "lastname" : "Shalpuk",
    "phoneNumber" : "+375291306639",
    "password" : "12345678",
    "email" : "kirill.shelpuk@gmail.com"
}
```
>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`

- ### Get page of all users:
> `GET -> "/users"`

>_Request body:_ `page`, `elements`, `sortDirection`, `sortBy`, `search` 

>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`


- ### Get user by id:
> `GET -> "/users/{userId}"`

>_Request parameters:_ `NONE`

>_Request body:_ `NONE`

>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`


- ### Delete user by id:
> `DELETE -> "/{users{/userId"`

>_Request parameters:_ `NONE`

>_Request body:_ `NONE`

>Authenticated: `ADMIN`, `SUPER ADMIN`


- ### Update user role:
> `PUT -> "/users/{userId}/updateRole"`

>_Request parameters:_ `NONE`

>_Request body:_
```java
{
    "role" : "ADMIN"
}
```
or
```java
{
    "role" : "VIEWER"
}
```

>Authenticated: `ADMIN`, `SUPER ADMIN`

- ### Update user:
> `PUT -> "/users/{userId}"`

>_Request parameters:_ `NONE`

>_Request body:_
```java
{
    "firstname" : "Kiryl",
    "lastname" : "Shalpuk",
    "phoneNumber" : "+375291306639",
    "password" : "12345678",
    "email" : "kirill.shelpuk@gmail.com"
}
```

>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`
