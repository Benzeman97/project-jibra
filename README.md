# Authorization_Code-Flow
implemented authorization_code flow grant type with spring security and spring cloud oauth2

Working flow
-------------------

http://127.0.0.1:8888/code ---------->  http://127.0.0.1:9090/oauth/authorize (you have to provide client_id and response_type as paramas)

---------------> http://127.0.0.1:9090/login (for authentication with authorization server) ------------>  http://127.0.0.1:9090/oauth/authorize (again redirect to get authorization)

-----------------> http://127.0.0.1:8888/private?code=#gs&89s (authorization token is provided by authorization server)  ----------- redirect to -------------

-----------------> http://127.0.0.1:9090/oauth/token?code=#gs&89s&grant_type=authorization_code (to get access token) 

----------------> http://127.0.0.1:9098/admin?Authorization = bearer "access-token as JWT" ( request to the resource server with access token )
