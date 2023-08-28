# GitApi

Simple Api for retrieving repositories for given GitHub user. This example handle headers via Interceptor with global error handling (@ControllerAdvice) 
<br>
### Technologies
SpringBoot 3, Java 17
### Steps to Set up
This step is optional, by providing your GitHub `token` you will access your private repositories. Without this step accessible are public repositories given valid GitHub Users `Login`.    

1. Create a personal access token on Github - https://github.com/settings/tokens
2. Open `src/main/resources/application.properties` and specify your `github` username in `name` property, and your personal access token in `token` property.

### How run app
First download this repository to your machine, then you can do one of below methods:
1. Open directory in cmd or powershell(or other command prompt) the command: `./gradlew bootRun`
2. Optionally you can generate executable jar file with command `./gradlew bootJar` then  `java -jar build/libs/GitApi-0.0.1.jar`
3. or open with your favorite IDE and run this api within IDE.

### What to call

When you start this api you can call endpoint `http://localhost:8080/api/v1/repos`. 
To successfully retrieve data GitHub via this api you need provide two things: 
1. Query Param `userName`: valid Username: `<login>`
2. Header: `Accept`:`application/json`
In case you do not provide valid username parameter you will receive error response: 
```json
 {
  "status": 404,
  "message": "Exception: User Not Found in GitHub"
}
```
3. In case providing  Header: `Accept`:`application/xml` you will receive response:
```json
{
    "status": 406,
    "message": "Exception: application/xml not supported"
}
```
Easiest to see results is use client such as Postman by providing above-mentioned Params and Header.<br>
Other option is to send in command prompt bellow code to restive json: 

`cURL -XGET http://localhost:8080/api/v1/repos?userName=<here provide userName> -H "Accept: application/json"`

