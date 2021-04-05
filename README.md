## JSON Base 64 Comparator API
### Technologies used

* Java OpenJDK 11
* Spring
* Docker and Docker compose
* Maven
* Mongo
* Swagger
* Loombok

### Description

This is an API that compares two base64 strings and gets the differences between them, differences in length and offset.
To get the result it is necessary to send data to the left and the right endpoint with the same valueId (more info about endpoints in [swagger](#How-to-use))
Then, it will be available in the get endpoint to get the differences between left adn right string within the provided **id**.

### Running
1. Make sure that you have installed all the necessary tools. 
   If you are running with docker.
    * Docker
    * Docker compose
    * Maven
   
   To run locally without docker you will need to install openJDK 11
      
         openjdk version "11.0.2" 2019-01-15

   together with mongo. [How to install mongo](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/)


2. Cloning this repo into your local


3. To run the project:

        mvn clean package

4. To run the project without tests:]

        mvn clean package -PskipTests

5. It will clean, compile and generate a jar at target dir. After that just run the docker-compose command:

        docker-compose up

This will bring the whole application up, with mongo as our NoSQL database.
To stop the appliaction the only thing that is needed it to run the following command:

      docker-compose down

### How to use

To check the endpoints available in this application, you can run it, following the steps above and access: 
    
      localhost:8080/swagger-ui.html/

This will display a swagger documentation of the available endpoints and their required body and params.
It is needed to send data to left and right endpoint:

**Left endpoint**

    {
        "value": "base64string"
    }

**Right endpoint**

    {
        "value": "base64string"
    }

Then get the differences using the get endpoint, providing the **id** that you passed in previous endpoints and you will get a response such as:

    {
        "id": "123456",
        "result": "EQUAL"
    }

Remembering the **differences** node will not be there if the strings are equal.

### Test

To run the tests, unit and integration:

    mvn test