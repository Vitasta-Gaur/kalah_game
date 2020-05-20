# Kalah
Java RESTful Web Service that runs a game of 6-stone Kalah.

#### API


- Swagger API documentation: http://localhost:8080/swagger-ui.html#/

- Create Game: 

```
curl --header "Content-Type: application/json" --request POST http://localhost:8080/games
```

- Make a move:
```
curl --header "Content-Type: application/json" --request PUT http://localhost:8080/games/{gameId}/pits/{pitId}
```

# How to run
If you want to run project on your local machine  , type the following command from the root directory:

```
mvn clean install spring-boot:run
```