# chatgpt-service-project

API details

curl --location --request POST 'http://localhost:8090/api/v1/bot/send' \
--header 'Content-Type: application/json' \
--data-raw '{
    "question": "What is gluten sensitivity?"
}'

response :

{
    "id": "cmpl-6odx9iLtH96KNhyq9FaPqDkyFyScZ",
    "object": "text_completion",
    "model": "text-davinci-003",
    "created": "+4594885-08-30",
    "choices": [
        {
            "index": 0,
            "text": "\n\nData is information that has been organized and formatted into a meaningful form. It can be used to answer questions, make decisions, and support research. Data can come in many forms, such as numbers, text, images, audio, and video.",
            "finish_reason": "stop"
        }
    ]
}
Installing Environement : 

install jdk 17
install spring boot 3 with maven
generate or cloning spring boot project using commange git clone ...
using maven dependecies 

deployment in docker container : 

To dockerize the application, we first create a file named Dockerfile with the following content:

FROM openjdk:17.0.1
EXPOSE 8888
ADD target/chatgpt-service.jar chatgpt-service.jar 
ENTRYPOINT [ "java","-jar","chatgpt-service.jar" ]

mvn clean package or mvn install or clic run as /mvn install

This file contains the following information:

FROM: As the base for our image, we'll take the Java-enabled Alpine Linux created in the previous section.
ADD: We let Docker copy our jar file into the image.
ENTRYPOINT: This will be the executable to start when the container is booting. We must define them as JSON-Array because we'll use an ENTRYPOINT in combination with a CMD for some application arguments.

To create an image from our Dockerfile, we have to run 'docker build' like before:

docker build . -f dockerFile -t chatgpt-service.jar

Finally, we're able to run the container from our image:

docker run -p8888:8090 chatgpt-service.jar:latest

This will start our application in Docker, and we can access it from the host machine at localhost:8090/api/v1/bot/send. Here it's important to define the port mapping, which maps a port on the host (8090) to the port inside Docker (8888). This is the port we defined in the properties/yml of the Spring Boot application.

Note: Port 8887 might not be available on the machine where we launch the container. In this case, the mapping might not work and we need to choose a port that's still available.

If we run the container in detached mode, we can inspect its details, stop it, and remove it with the following commands:

 docker inspect message-server
 docker stop message-server
 docker rm message-server


