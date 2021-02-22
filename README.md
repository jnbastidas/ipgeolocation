# ipgeolocation
Java app to expose a rest service that let us process a plain text file (CSV) and load the data into a database, another rest service to consult the information.


<b>Running the Ipgeolocation Java app locally:</b>

PREREQUISITES

First, download the Java 11 Development Kit: either the official Oracle JDK or Open JDK.</br>
You will also need a MySql server running.

Steps

1. CREATE database and user in MySql Server

  Please login with a root user in your MySql server and in the mysql console execute the next file content:
  <a href='https://github.com/jnbastidas/ipgeolocation/blob/develop/src/main/resources/mysql_database_init.sql'>mysql_database_init.sql</a>


1. INSTALL ipgeolocation Application

  a) Clone and download this git repository: https://github.com/jnbastidas/ipgeolocation.git (keep note of the location).</br>
  b) Checkout master branch.</br>
  c) At the command line for your operating system, change your directory to where the source was downloaded, and build the project:

  For Windows:
    
      » gradlew.bat clean
      » gradlew.bat build
  
  For MacOs:
  
      » ./gradlew clean
      » ./gradlew build
      

2. START APP (Java web app)

  In the same directory to the previous step, from the command line and execute:

  For Windows:
  
      » gradlew.bat run

  For MacOs:
  
      » ./gradlew run
  
  
  Once this has finished, you can use the url http://localhost:8080/ipgeolocalization/ping to verify the correct work, the service should response "pong"
  
  Please download and import the file <a href='https://github.com/jnbastidas/ipgeolocation/blob/develop/src/main/resources/IpGeolocation.postman_collection.json'>Ipgeolocation.postman_collection.json</a> with the <a href='https://www.postman.com/'>Postman app</a> to test the endpoints.
  
  
  <b>Key Features and Technologies</b>
  

