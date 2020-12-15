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

How to run it at docker :

First you need install Docker deamon, Docker compose and SET bash to run from terminal for Windows OS : 
- https://docs.docker.com/docker-for-windows/ 
- https://docs.docker.com/compose/install/

Git clone to your local machine
```sh
$ git clone https://github.com/trongtb88/grpc-microservice.git
```
Build and docker all images : 
- Check all application.properties file for each service, need comment and uncomment like that 
```sh
eureka.client.serviceUrl.defaultZone  = http://eurekaserver:8761/eureka
#eureka.client.serviceUrl.defaultZone  = http://localhost:8761/eureka
bootstrap.servers=kafka:9092
```

```sh
$ cd grpc-microservice
$ mvn clean package -Dmaven.test.skip=true
$ docker-compose up --build
```
It will take some minutes to download dependencies and build image, after build successfuly, you need to run 2 below commands.

```sh
$ docker-compose up
```
It also take some minutes to bring up springboot app, when you see information below, that means it worked :

app-server_1 | 2020-12-02 06:08:41.444 INFO 1 --- [ main] o.s.b.w.embedded.tomcat.TomcatWebServer : Tomcat started on port(s): 8080 (http) with context path '' app-server_1 | 2020-12-02 06:08:41.448 INFO 1 --- [ main] com.thanh.SpringGrpcDemoApplication : Started SpringGrpcDemoApplication in 7.55 seconds (JVM running for 8.158) app-server_1 | 2020-12-02 06:08:41.450 INFO 1 --- [ main] o.l.springboot.grpc.GRpcServerRunner : Starting gRPC Server ... app-server_1 | 2020-12-02 06:08:41.503 INFO 1 --- [ main] o.l.springboot.grpc.GRpcServerRunner : 'com.thanh.controller.SeatController' service has been registered. app-server_1 | 2020-12-02 06:08:41.803 INFO 1 --- [ main] o.l.springboot.grpc.GRpcServerRunner : gRPC Server started, listening on port 6565.

To see data in mysql database : 
run below command interminal
```sh
$ mysql -h localhost -P 3306 --protocol=tcp -u dea_spring_user -p dea_spring_user
```

To call grpc server from client, you can use BloomGrpc
- https://github.com/uw-labs/bloomrpc/blob/master/README.md
- Choose proto file
- Copy & Paste localhost:6565 to Textbox address and run command

To check kafka work or not, download Kafka tool and connect from localhost
- https://www.kafkatool.com/
- https://kafka.apache.org/quickstart


