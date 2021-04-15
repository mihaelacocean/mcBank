Access http://localhost:8080/swagger-ui.html#/
3 endpoints will be available:
`/users/login - POST method`
`/users/{customerId}/accounts/current - POST method`
`/users/{id} - GET method`

#Login method#
Retrieves a authentication header for an existing user
Make a POST call to  `/users/login`
      Required attributes: - Request body containing users credentials. Credentials can be found here:
            - Admin credentials: https://github.com/mihaelacocean/mcBank/blob/master/src/main/resources/data.sql#L50
            - Normal user credentials: https://github.com/mihaelacocean/mcBank/blob/master/src/main/resources/data.sql#L45

Use cases:
     1. Successful operation - Authentication token for the user is returned
     2. Error cases:
        - Error code 400 - Invalid request
                - Credentials not provided
        - Error code 401 - Unauthorized
                - Invalid credentials


#Open account for existing customer#
Admin users can make a call for an existing customer to open a current account. In case initial credit is valid positive amount, a transaction is done for the created account
Make a POST call to    `/users/{customerId}/accounts/current`
      Required attributes: - customerId - existing user ID. Available values can be taken from DB: https://github.com/mihaelacocean/mcBank/blob/master/src/main/resources/data.sql#L42
                           - authorization header - valid authorization token for user making the call (token retrieved by login method)
      Optional attributes - initialCredit request body as numeric positive value
 Use cases:
    1. Successful operation - ID of the created account is returned
    2. Error cases:
        - Error code 400 - Invalid request
                - Invalid customerId provided
                - Negative initial amount
                - Customer already has a current account open
                - Authorization header is not set
        - Error code 401 - Unauthorized
                - Authorization header is invalid, no existing user corresponding
        - Error code 403 - Forbidden
                - User making the request is not admin


#Display existing user#
An user makes the call for requesting info about an existing customer, by id. User's accounts and transactions are displayed as well
Make a GET call to  `/users/{id}`
      Required attributes: - userId - existing user ID. Available values can be taken from DB: https://github.com/mihaelacocean/mcBank/blob/master/src/main/resources/data.sql#L42
                           - authorization header - valid authorization token for user making the call (token retrieved by login method)
Use cases:
     1. Successful operation - requested user is displayed
     2. Error cases:
        - Error code 400 - Invalid request
                - User id not provided
                - Authorization header is not set
        - Error code 401 - Unauthorized
                - Authorization header is invalid, no existing user corresponding
        - Error code 403 - Forbidden
                - Non admin user trying to access information for a different user


Example calls:

1.1 Admin Login successful
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: text/plain' -d '{ \
   "password": "admin", \
   "username": "admin" \
 }' 'http://localhost:8080/users/login'
 ```
1.2 Normal user login successful:
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: text/plain' -d '{ \
   "password": "pass4mary", \
   "username": "mary.sullivan" \
 }' 'http://localhost:8080/users/login'
```
1.3 Invalid credentials:
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ \
   "password": "invalid", \
   "username": "invalid" \
 }' 'http://localhost:8080/users/login'
```

2.1 Open account for existing user
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'authorization: owjdwkldfqj@6dm' -d '230' 'http://localhost:8080/users/3/accounts/current'
```

2.2 Open account non admin user
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'authorization: jkahjdkhfkvo658' -d '230' 'http://localhost:8080/users/2/accounts/current'
```

2.3 Open account invalid authorization header
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'authorization: invalid' -d '230' 'http://localhost:8080/users/2/accounts/current'
```

2.4 Open account customer not found
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'authorization: owjdwkldfqj@6dm' -d '230' 'http://localhost:8080/users/7/accounts/current'
```

2.5 Open account negative initial amount
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'authorization: owjdwkldfqj@6dm' -d '-230' 'http://localhost:8080/users/2/accounts/current'
```

2.6 Open account user already has a current account - repeat operation mentioned at 2.1
```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'authorization: owjdwkldfqj@6dm' -d '230' 'http://localhost:8080/users/3/accounts/current'

```

3.1 Get user by id
```
curl -X GET --header 'Accept: application/json' --header 'authorization: owjdwkldfqj@6dm' 'http://localhost:8080/users/1'

```
3.2 Get user not found
```
curl -X GET --header 'Accept: application/json' --header 'authorization: owjdwkldfqj@6dm' 'http://localhost:8080/users/7'

```
3.3 Get user invalid authorization header
```
curl -X GET --header 'Accept: application/json' --header 'authorization: invalid' 'http://localhost:8080/users/3'
```
3.4 Get user requested by non admin for a different user
```
curl -X GET --header 'Accept: application/json' --header 'authorization: jkahjdkhfkvo658' 'http://localhost:8080/users/3'
```

3.5 Get current user non admin user
```
curl -X GET --header 'Accept: application/json' --header 'authorization: jkahjdkhfkvo658' 'http://localhost:8080/users/2'
```