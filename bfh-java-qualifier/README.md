# Bajaj Finserv Health | JAVA Qualifier — Ready Repo

This repo is a **ready-to-run Spring Boot app** that completes the Qualifier flow **automatically on startup**:

1. Calls **generateWebhook** to get a `webhook` URL and `accessToken` (JWT).
2. Chooses **SQL Question** from `regNo` last two digits (odd → Q1, even → Q2).
3. Solves the SQL and **POSTs** the `finalQuery` JSON to the returned **webhook** using the token in the `Authorization` header.

> No controller/endpoint triggers the flow; it runs via a `CommandLineRunner` at startup. Uses `RestTemplate`.

---

## Quick Start

### 1) Configure your details
Update `src/main/resources/application.properties`:

```properties
app.name=Your Name
app.regNo=REG12346
app.email=you@example.com
```

You can also override via environment variables:
- `APP_NAME`, `APP_REGNO`, `APP_EMAIL`

### 2) Build
```bash
mvn -q -DskipTests clean package
```

### 3) Run
```bash
java -jar target/bfh-java-qualifier-1.0.0.jar
```

---

## Files to Edit (if needed)

- `SQLSolverService.java`: put your final SQL for Q1/Q2.
- `application.properties`: your name, regNo, email.

By default, **Q2** (even regNo) contains the correct SQL for the given PDF:

```sql
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

---

## What gets logged

- Request/response summaries for webhook generation and submission
- Final decision of which SQL was sent

> Sensitive values (token) are not fully logged.

---

## Submission Checklist (from problem)

- Public GitHub repo with code + final JAR + raw JAR link
- Public downloadable JAR link
- Submit the link in the Microsoft Forms

Good luck!
