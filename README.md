# Java for Enterprise Applications Exercice 4 

## Set-up

### Requirements

- Java 21
- Maven
- Docker

When those are fulfilled you can launch the application by first

1. Creating a docker container containing the Redis database by using following command in the root directory of the application

```
docker compose up -d
```

2. Once the docker container is running, the application can be launched using

```
mvn clean spring-boot:run
```


## Accessing the application

Once the application is running, you can go to the Graphiql, which is a in-browser tool to test out the GraphQL queries, which can be accessed on:

```
localhost:8080/graphiql
```

## Testing the application

### Creating a user

```
mutation{
    createUser(name: "John Doe", email: "johndoe@gmail.com", phone: "123456789"){
        id
        name
        email
        phone
    }
}
```

**Result**:
```
{
  "data": {
    "createUser": {
      "id": "87cc3e8a-bc66-424c-91fe-32f5db2cdfd2",
      "name": "John Doe",
      "email": "johndoe@gmail.com",
      "phone": "123456789"
    }
  }
}
```

### Retrieving the users

There are 2 implmentations to retrieve users:

1. By querying the user by the id

```
query{
  getUserById(id:"87cc3e8a-bc66-424c-91fe-32f5db2cdfd2"){
    id
    name
    email
    phone
  }
}

This should get you the exact same result as above
```

2. By querying the user by a name (allows for partial matches and returns a list), which was mainly introduced for the ease of retrieving the user ids for following queries

```
query {
  getUserByName(name:"John"){
    id
    name
    email
    phone
  }
}
```

**Result**:
```
{
  "data": {
    "getUserByName": [
      {
        "id": "87cc3e8a-bc66-424c-91fe-32f5db2cdfd2",
        "name": "John Doe",
        "email": "johndoe@gmail.com",
        "phone": "123456789"
      },
      {
        "id": "f4c334f6-72e7-4fc4-a083-6538385c5749",
        "name": "John Doe 2",
        "email": "johndoe@gmail.com",
        "phone": "123456789"
      }
    ]
  }
}
```
### Creating a tweet

A tweet can be created using following query:

```
mutation{
  postTweet(title:"This is my first tweet", message:"Hello world", authorId:"87cc3e8a-bc66-424c-91fe-32f5db2cdfd2", validityLengthInDays:10){
    id
    title
    message
    author{
      id
      name
    }
    validityLengthInDays
  }
}
```

**Return**:
```
{
  "data": {
    "postTweet": {
      "id": "986a55c4-dd07-4e3e-ada6-632e59a8bc03",
      "title": "This is my first tweet",
      "message": "Hello world",
      "author": {
        "id": "87cc3e8a-bc66-424c-91fe-32f5db2cdfd2",
        "name": "John Doe"
      },
      "validityLengthInDays": 10
    }
  }
}
```
### Creating feedback for a tweet

Feedback for a tweet can be added by using following query, which will embed the feedback object inside the tweet for improved performance

```
mutation{
  addFeedback(tweetId:"986a55c4-dd07-4e3e-ada6-632e59a8bc03",message:"This is awesome", authorId:"87cc3e8a-bc66-424c-91fe-32f5db2cdfd2"){
    id
    title
    message
    author{
      id
      name
    }
  	feedback{
      message
      author{
        id
        name
      }
    }
  }
}
```
**Result**:
```
{
  "data": {
    "addFeedback": {
      "id": "986a55c4-dd07-4e3e-ada6-632e59a8bc03",
      "title": "This is my first tweet",
      "message": "Hello world",
      "author": {
        "id": "87cc3e8a-bc66-424c-91fe-32f5db2cdfd2",
        "name": "John Doe"
      },
      "feedback": [
        {
          "message": "This is awesome",
          "author": {
            "id": "87cc3e8a-bc66-424c-91fe-32f5db2cdfd2",
            "name": "John Doe"
          }
        }
      ]
    }
  }
}
```
### Searching tweet

A tweet can be retrieved by entering a keyword which is either in the title or the message like the following:

```
query{
  searchTweets(query:"hello"){
    id
    title
    message
  }
}
```
**Result**
```
{
  "data": {
    "searchTweets": [
      {
        "id": "986a55c4-dd07-4e3e-ada6-632e59a8bc03",
        "title": "This is my first tweet",
        "message": "Hello world"
      },
      {
        "id": "a8595c3a-abea-40fb-baa7-2598096004b6",
        "title": "Hello World",
        "message": "This is my second tweet"
      }
    ]
  }
}
```

# Demo

The demo can be inspected on following YT-link: https://youtu.be/9L8L1ZUod54