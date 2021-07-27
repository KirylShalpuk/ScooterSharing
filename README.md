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
  - [Delete user by id](#delete-user-by-id)
  - [Update user role](#update-user-role)
  - [Update user by id](#update-user-by-id)
- [SCOOTER requests](#scooter-requests)
  - [Create scooter](#create-scooter)
  - [Get scooter filter properties](#get-scooter-filter-properties)
  - [Get page of all scooters](#get-page-of-all-scooters)
  - [Get scooter by id](#get-scooter-by-id)
  - [Delete scooter by id](#delete-scooter-by-id)
  - [Update user scooter id](#update-user-scooter-id)

## AUTH requests
- ### Login:

> `POST -> "/auth/login"`

>_Request parameters:_ `NONE`

>_Request body:_
```json
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
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdXBlci5hZG1pbkBzY29vdGVyLmNvbSIsImV4cCI6MTYyNzM4NzAyMX0.HnViKzhdiv8ek0PzrK35KiKSZB9C5XRbRvHZlVdZHVdDSsSrAx4PlhZxP5Wc6RsWv3T7pJJFcWqtuiWnXPbOHQ"
}
```
>Authenticated: `NONE`


## USER requests
- ### Create user
> `POST -> "/users"`

>_Request parameters:_ `NONE`

>_Request body:_
```json
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

>_Request parameters:_ `page`, `elements`, `sortDirection`, `sortBy`, `search` 

>_Request body:_ `NONE`

>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`


- ### Get user by id:
> `GET -> "/users/{userId}"`

>_Request parameters:_ `NONE`

>_Request body:_ `NONE`

>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`


- ### Delete user by id:
> `DELETE -> "/{users}/userId"`

>_Request parameters:_ `NONE`

>_Request body:_ `NONE`

>Authenticated: `ADMIN`, `SUPER ADMIN`


- ### Update user role:
> `PUT -> "/users/{userId}/updateRole"`

>_Request parameters:_ `NONE`

>_Request body:_
```json
{
    "role" : "ADMIN"
}
```
or
```json
{
    "role" : "VIEWER"
}
```
>Authenticated: `ADMIN`, `SUPER ADMIN`

- ### Update user by id:
> `PUT -> "/users/{userId}"`

>_Request parameters:_ `NONE`

>_Request body:_
```json
{
    "id": "447a319e-1f0f-42f2-b74d-c2d240e9f795",
    "firstname": "Kiryl",
    "lastname": "Shalpuk",
    "phoneNumber": "+48798743379",
    "email": "kirill.shelpuk@gmail.com",
    "role": "VIEWER",
    "active": true
}
```
>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`

## SCOOTER requests
- ### Create scooter:
> `POST -> "/scooters"`

>_Request parameters:_ `NONE`

>_Request parameters:_ `NONE`

>_Request body:_
```json
{
        "manufacturer": "Xiaomi",
        "model": "Mi zKg",
        "photoUrl": null,
        "batteryCharge": 42,
        "lastService": 1620603797640,
        "softwareVersion": "1.1.24",
        "active": true,
        "charging": true,
        "currentLocation": {
          "id": "d3cba917-b799-4494-b8f6-c12bfab747b2",
          "country": "Polska",
          "city": "Warszawa",
          "street": "ulica Czeremchowa",
          "building": "28C",
          "coordinates": {
            "id": "466da1ba-4b72-4f2b-b53c-dcc6146d475c",
            "latitude": "52.3432968",
            "longitude": "20.9613521"
          }
        }
}
```
>Authenticated: `ADMIN`, `SUPER ADMIN`


- ### Get scooter filter properties:
> `DELETE -> "/scooters/filter"`

>_Request parameters:_ `NONE`

>_Request body:_ `NONE`

>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`


- ### Get page of all scooters:
> `GET -> "/scooters"`

>_Request parameters:_ `page`, `elements`, `sortDirection`, `sortBy`

>_Request body:_ 
```json
{
    "manufacturers" : ["Xiaomi"],
    "models" : [
        "Mi voB",
        "Mi lnl"
        ],
    "batteryChargeFrom": 30,
    "batteryChargeTo": 50,
    "active" : false,
    "locationAddress": [
        "ulica Kąty Grodziskie",
        "ulica Echa Leśne"
        ]
}
```
>Authenticated: `ADMIN`, `SUPER ADMIN`


- ### Get scooter by id:
> `GET -> "/scooters/{scooterId}"`

>_Request parameters:_ `NONE`

>_Request body:_ `NONE`

>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`


- ### Delete scooter by id:
> `DELETE -> "/scooters/{scooterId}"`

>_Request parameters:_ `NONE`

>_Request body:_ `NONE`

>Authenticated: `ADMIN`, `SUPER ADMIN`


- ### Update user scooter id:
> `PUT -> "/scooters/{scooterId}"`

>_Request parameters:_ `NONE`

>_Request body:_
```json
{
  "id": "90e947c5-bd0a-4fc3-95cf-623577a5a241",
  "manufacturer": "Xiaomi",
  "model": "Mi zKg",
  "photoUrl": null,
  "batteryCharge": 42,
  "lastService": 1620603797640,
  "softwareVersion": "1.1.24",
  "active": true,
  "charging": true,
  "currentLocation": {
    "id": "d3cba917-b799-4494-b8f6-c12bfab747b2",
    "country": "Polska",
    "city": "Warszawa",
    "street": "ulica Czeremchowa",
    "building": "28C",
    "coordinates": {
      "id": "466da1ba-4b72-4f2b-b53c-dcc6146d475c",
      "latitude": "52.3432968",
      "longitude": "20.9613521"
    }
  }
}
```
>Authenticated: `USER`, `ADMIN`, `SUPER ADMIN`
