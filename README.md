# RaboBank Statement Process #


* Overview 

   Rabobank receives monthly deliveries of customer statement records. This information is delivered in two formats, CSV and XML. These records need to be validated using below conditions
  
     * all transaction references should be unique
     * end balance needs to be validated 

* Version 1.0


**1. Clone the repository** 

```bash
git clone https://github.com/sakthivelganesan/rabobank-statement-processor.git
```

**2. Run the app using maven**

```bash
cd rabobank-statement-processor
mvn spring-boot:run
```

```bash
mvn clean package
java -jar target/rabobank-statement-processor-1.0.0-RELEASE.jar
```

Application runs on `http://localhost:8080`.

Application End Point Details `http://localhost:8080/swagger-ui.html`.



