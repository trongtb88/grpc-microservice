# grpc-microservice
How to run it at localhost

1. cd kafkaserver folder, run docker-compose up to run kafka to dev
2. Use kafka tool or command to create topic (veftopic) in config file (userservice)
- https://www.kafkatool.com/
- https://kafka.apache.org/quickstart
3. go to root folder, run mvn install package -Dmaven.test.skip=true  
4. go to properties file to change properties email username,password of notification service
5. run eurekaservice -> seatservice -> userservice -> notification service
6. use Post man call to make everything work

http://localhost:7002/users/reserves/new

Sample body 

{
    "id" : 1,
    "name": "Trong Tran",
    "email" : "trongtb1988@gmail.com",
        "seats": [
    {
      "row": 1,
      "column": 1,
      "status": "empty"
    }
  ]    
}

