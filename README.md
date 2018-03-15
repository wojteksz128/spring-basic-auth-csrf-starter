# Spring-Basic-Auth-CSRF-Starter
Simple Spring Boot example exaplined how to protect REST API using Basic Authentication with CSRF. 

### Steps
1. Run GET request on http://localhost:8080/api/hello Response is 401.
2. Run POST request on http://localhost:8080/login with Authentication header using user/user credentials. In response you will receive the XSRF-TOKEN token in the cookie and the x-auth-token header.
3. Run GET request on http://localhost:8080/api/hello with XSRF-TOKEN token, x-auth-token and authentication in header. 
