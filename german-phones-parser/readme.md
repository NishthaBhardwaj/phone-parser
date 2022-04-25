# German  Phones Parser API

## Requirements


- [JDK 11](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)


### Building and deploying the application

### Building the application

The project uses [Maven](https://maven.apache.org/) as a build tool. It already contains ./mvnw wrapper script, so there's no need to install maven.

To build the project execute the following command:

```bash
  ./mvnw clean install 
```

### Running the application

```bash
  ./mvnw spring-boot:run
```
### Alternative way to run application using docker-compose
To skip all the setting up and building, just execute the following command:


```bash
docker-compose up
```


This will start the API container exposing the application's port
(set to `9000`).

### Tesing Application
In order to test if the application is up, you can call its base url:

```bash
curl http://localhost:9000
```

You should get a response similar to this:

```
{healthy:true}
```

cleaned up the resources by this command:

```bash
docker-compose rm
```

It clears stopped containers correctly. Might consider removing clutter of images too, especially the ones fiddled with:

```bash
docker images

docker image rm <image-id>
```

##Swagger URL
http://localhost:9000/swagger-ui.html

