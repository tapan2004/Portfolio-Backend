# Portfolio Backend API

This is the backend service for my personal portfolio website.
It is built using **Spring Boot** and provides REST APIs to manage portfolio data such as projects, contact messages, and other dynamic content.

The application is deployed on Render and is designed following clean architecture and RESTful API principles.

---

## 🚀 Live API

Base URL: https://portfolio-api-latest-2.onrender.com/
Swagger URL: https://portfolio-api-latest-2.onrender.com/swagger-ui/index.html

---

## 🛠️ Tech Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* MySQL / PostgreSQL
* Maven
* REST API
* Render (Deployment)
* GitHub (Version Control)

---

## 📂 Project Structure

src/main/java/com/portfolio
│
├── controller
│   └── ContactController.java
│
├── service
│   └── ContactService.java
│
├── repository
│   └── ContactRepository.java
│
├── model
│   └── ContactMessage.java
│
└── PortfolioApplication.java

---

## 📌 Features

* RESTful API for portfolio projects
* Contact form message handling
* Email sending functionality
* Input validation using @Valid
* Exception handling
* Clean layered architecture
* Production deployment on Render

---

## 🔧 API Endpoints

### Contact API

POST /api/contact

Request Body:

{
"name": "John Doe",
"email": "[john@example.com](mailto:john@example.com)",
"message": "Hello, I am interested in your work."
}

Response:

Message sent successfully

---

### Project API

GET /api/projects

Returns a list of portfolio projects.

---

## ⚙️ How to Run Locally

1. Clone the repository

git clone https://github.com/tapan2004/portfolio-backend.git

2. Navigate to project directory

cd portfolio-backend

3. Configure database

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/portfolio_db
spring.datasource.username=root
spring.datasource.password=your_password

4. Run the application

mvn spring-boot:run

---

## 🌐 Deployment

The application is deployed using Render.

Steps:

* Push code to GitHub
* Connect GitHub repository to Render
* Configure environment variables
* Deploy automatically

---

## 📬 Contact

Name: Tapan Manna
Email: [mannatapan588@gmail.com](mailto:mannatapan588@gmail.com)
GitHub: https://github.com/tapan2004

---

## 📖 Future Improvements

* Add authentication (JWT)
* Add admin dashboard
* Add file upload support
* Improve logging and monitoring

---

## ⭐ Author

Tapan Manna
Backend Developer | Java | Spring Boot
