# 🛡️ Spring Boot HTTP Forward Proxy

A lightweight HTTP Forward Proxy built using Spring Boot that allows forwarding requests to external APIs dynamically using a custom header.

---

## 🚀 Features

- ✅ Supports HTTP methods: `GET`, `POST`, `PUT`, `PATCH`
- ✅ Uses custom header `X-Target-URL` to redirect requests
- ✅ Copies all headers and query parameters
- ✅ Handles both binary (e.g. image) and text responses
- ✅ Simple, fast, and easy to extend
- ✅ Dockerized for container-based deployment

---

## 📦 Usage

### 🔁 Send a GET request

```bash
  curl -X GET http://localhost:8080/ \
  -H "X-Target-URL: https://httpbin.org/get"
```
---
### 🔁 Send a POST request

```bash
  curl -X POST http://localhost:8080/ \
-H "X-Target-URL: https://postman-echo.com/post" \
-H "Content-Type: application/json" \
-d '{"name": "shivam", "role": "developer"}'
```
---
### 🛠️ Tech Stack
#### Java 17+

#### Spring Boot 3+

#### Unirest (for making outbound HTTP calls)

#### Docker (for containerization)

---

### 📌Next Features (To-Do)
#### Retry mechanism for failed target calls

#### Rate limiting / throttling support

#### Header whitelisting / blacklisting

#### OpenTelemetry / distributed tracing

---

### 🔗 Author
Shivam Srivastav

<https://www.linkedin.com/in/imshiivam/>






