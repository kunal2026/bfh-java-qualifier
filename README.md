# Bajaj Finserv Health | JAVA Qualifier
This project is a Spring Boot application that automates the Bajaj Finserv Health Qualifier process. The application runs the complete flow on startup without requiring any controller endpoints.

# Features
    Sends a POST request to generate a webhook on startup.
    
    Retrieves webhook URL and accessToken.
    
    Determines the SQL problem based on the last two digits of regNo.
    
    Generates the required SQL query and submits it to the webhook using the JWT token.
    
    Executes automatically through a CommandLineRunner.

# Requirements
    Java 17 or higher
    
    Maven 3.9+ (for build)

# Build

```
mvn clean package -DskipTests
```

This generates the executable JAR in the target/ folder:
target/bfh-java-qualifier-1.0.0.jar


Run
```
java -jar target/bfh-java-qualifier-1.0.0.jar
```


app.name=KUNAL MUKHERJEE
app.regNo=22BAI1472
app.email=km820366@gmail.com

SQL Solution (Question 2 - Even Reg.No)

```
SELECT 
    e1.EMP_ID,
    e1.FIRST_NAME,
    e1.LAST_NAME,
    d.DEPARTMENT_NAME,
    COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT
FROM EMPLOYEE e1
JOIN DEPARTMENT d 
    ON e1.DEPARTMENT = d.DEPARTMENT_ID
LEFT JOIN EMPLOYEE e2
    ON e1.DEPARTMENT = e2.DEPARTMENT
   AND e2.DOB > e1.DOB
GROUP BY 
    e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME
ORDER BY 
    e1.EMP_ID DESC;

```

# Submission
    GitHub repository link - https://github.com/kunal2026/bfh-java-qualifier/tree/main/bfh-java-qualifier
    
    Public JAR download link (from GitHub Releases) - https://github.com/kunal2026/bfh-java-qualifier/releases/download/v1.0.0/bfh-java-qualifier-1.0.0.jar
