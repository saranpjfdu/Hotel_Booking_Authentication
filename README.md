# 🏨 Hotel Booking System (Authentication + Authorization + Frontend)

A full-stack hotel booking system with **JWT-based authentication**, **role-based authorization**, and a modern **React frontend**.

---

## 🚀 Features

### 🔐 Authentication (Backend - Spring Boot)

* User Registration & Login
* JWT Token Generation
* Secure API endpoints using Spring Security
* Stateless session management

### 🛡️ Authorization

* Role-based access control
* Roles:

  * `USER`
  * `HOTEL_MANAGER`
* Endpoint protection using:

  * `hasRole("HOTEL_MANAGER")`
  * `authenticated()`

---

## 🧠 Tech Stack

### Backend

* Java 17+
* Spring Boot
* Spring Security
* JWT (io.jsonwebtoken)
* JPA / Hibernate
* MySQL

### Frontend

* React (Vite)
* Axios
* Material UI (MUI)

---

## 📂 Project Structure

```
Hotel_Booking_Authentication/
│
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── security/
│   │   ├── JwtAuthFilter
│   │   ├── JwtService
│   │   ├── SecurityConfig
│   │   └── CustomUserDetailsService
│   └── entity/
│
├── frontend/
│   ├── src/
│   │   ├── pages/
│   │   ├── auth/
│   │   ├── api/
│   │   └── routes/
│
└── README.md
```

---

## 🔑 Authentication Flow

1. User logs in via `/api/auth/login`
2. Server validates credentials
3. JWT token is generated
4. Token sent to frontend
5. Frontend stores token
6. Token sent in header for API calls:

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 🛡️ Authorization Flow

* JWT filter validates token
* Extracts user + role
* Sets authentication in SecurityContext
* Spring Security checks roles

Example:

```
POST /api/hotels → HOTEL_MANAGER only
GET /api/hotels → Public
GET /api/bookings → Authenticated users
```

---

## ⚙️ Backend Setup

### 1️⃣ Clone repo

```
git clone https://github.com/saranpjfdu/Hotel_Booking_Authentication.git
```

### 2️⃣ Navigate

```
cd backend
```

### 3️⃣ Configure application.properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secret=your_secret_key
jwt.expiration=86400000
```

### 4️⃣ Run backend

```
mvn spring-boot:run
```

---

## 💻 Frontend Setup

### 1️⃣ Navigate

```
cd frontend
```

### 2️⃣ Install dependencies

```
npm install
```

### 3️⃣ Run frontend

```
npm run dev
```

---

## 🌐 API Endpoints

### 🔐 Auth

* `POST /api/auth/register`
* `POST /api/auth/login`

### 🏨 Hotels

* `GET /api/hotels`
* `POST /api/hotels` (HOTEL_MANAGER)
* `PUT /api/hotels/{id}`
* `DELETE /api/hotels/{id}`

### 📦 Bookings

* `GET /api/bookings`
* `POST /api/bookings`

---

## 🧪 Testing

Use Postman:

* Add header:

```
Authorization: Bearer <token>
```

---

## 📌 Notes

* Roles stored as ENUM (`USER`, `HOTEL_MANAGER`)
* Spring automatically adds `ROLE_` prefix
* JWT filter handles authentication

---

## 🎯 Future Improvements

* Payment integration
* Email notifications
* Admin dashboard
* Deployment (Docker + Cloud)

---

## 👨‍💻 Author

**Saran**

---
