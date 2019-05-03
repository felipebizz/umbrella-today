Umbrella Today API Client 
=================
Java Project that return if you need to take your umbrella according to latitude and longitude.
   
   #### Used Techonologies
   
   * Spring-boot
   * unirest-java
   * ehcache'
   * gson


Clone this repo and start it (on windows systems use the gradlew.bat file):

`./gradlew bootRun`

To view the assignment (after starting the application) go to:

[http://localhost:9000/forecast/index.html](http://localhost:9000/forecast/index.html)



`GET forecast/umbrella?latitude=51.5144&longitude=-0.0941`
 
 Response sample
 
        {
        "umbrella": yes,
        "weather": {
            "precipProbability": 0.9,
            "precipType": "rain"
        },
        "location": {
            "latitude": 51.5144,
            "longitude": -0.0941
        }
        }