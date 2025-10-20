# 🚀 TradeSphere Application

## 📄 Description

TradeSphere is a modern full-stack web application designed for **portfolio management and trading simulation**, built with **Angular** for the frontend and **Spring Boot** for the backend, with **MySQL** as the persistence layer. It delivers a **secure**, **scalable**, and **user-friendly** trading experience.

### ✅ Features at a Glance

* 🔐 **Authentication & Security** — Login with email/password, JWT authorization, role-based access (ADMIN, CLIENT)
* 👤 **User & Role Management**
* 📊 **Portfolio Management** — Create, update, delete, and track portfolios
* 💹 **Real-Time Market Visualization** — TradingView widgets integrated for live charts and financial insights
* 📱 **Responsive UI** — Optimized for both desktop and mobile users with a clean design

---

## 🛠️ Tech Stack

| Layer             | Technology                                                 |
| ----------------- | ---------------------------------------------------------- |
| **Frontend**      | Angular 17, Angular Material, RxJS, Bootstrap              |
| **Backend**       | Spring Boot, Spring Security, JWT, Maven                   |
| **Database**      | MySQL                                                      |
| **Visualization** | TradingView Widgets (Ticker Tape, Advanced Charts, Quotes) |

---

## 🚀 Installation & Setup

### 🔽 Clone the repository

```bash
git clone https://github.com/chahabsarah/TradeSphere.git
cd TradeSphere
```

### ⚙️ Configure MySQL in Backend

Edit `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tradesphere_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### ▶ Run Backend (Spring Boot)

```bash
cd Trading_spring
mvn spring-boot:run
# or
./mvnw spring-boot:run
```

👉 Backend runs at: `http://localhost:8080`

### 💻 Setup Frontend (Angular)

```bash
cd Trading_angular
npm install
ng serve -o
```

👉 Frontend runs at: `http://localhost:4200`

> ⚠ Make sure **MySQL**, **Backend**, and **Frontend** are running together.

---

## 🌐 Main Functionalities

### 🔐 Authentication & Roles

* Secure login with JWT tokens
* Role-based routing (ADMIN areas vs CLIENT areas)
* Optional OTP verification for extra security

### 📊 Portfolio Management

* Users can create and manage their own portfolios
* CRUD operations with real-time updates

### 📈 Trading & Market Analytics

* **TradingView Ticker Tape** → Live market price feed
* **Market Quotes Widget** → Sector-based financial summaries
* **Advanced Chart Widget** → Candlestick, volume, and trend analysis

---

## 📁 Global Project Structure

```
TRADESPHERE/
├── Trading_angular/      # Angular Frontend
└── Trading_spring/       # Spring Boot Backend
```

---

## 🎯 Backend Architecture — `Trading_spring`

```
Trading_spring/
├── src/main/java/tn/esprit/fundsphere
│   ├── Controllers/        # REST API Endpoints
│   ├── dto/                # Data Transfer Objects (request/response models)
│   ├── Entities/           # JPA Entities (Database Models)
│   ├── Repositories/       # Data Access Layer (CRUD interfaces)
│   ├── security/           # JWT, Filters, Security Config
│   ├── Services/           # Business Logic Layer
│   ├── utils/              # Helper Functions & Configs
│   └── FundsphereApplication.java  # Main Spring Boot Class
│
├── src/main/resources/     # Config Files & SQL Scripts
└── pom.xml                 # Dependencies & Build Config
```

---

## 🎨 Frontend Architecture — `Trading_angular`

```
Trading_angular/
├── src/app
│   ├── auth/                # Login, Guards, Interceptors
│   ├── components/         # Reusable UI Components
│   ├── pages/              # Page-Level Views
│   ├── services/           # API Communication via HttpClient
│   ├── shared/             # Interfaces, Models, Utils
│   ├── app-routing.module.ts # Application Routes
│   ├── app.module.ts        # Root App Module
│   ├── app.component.*      # Main App Component
│
├── assets/                 # Icons, Styles, Images
├── environments/           # Backend URL Configs
├── angular.json            # Angular CLI Config
├── package.json            # Dependencies & Scripts
└── tsconfig.json           # TypeScript Config
```

---

## 📌 Notes

* ✅ TradingView Widgets require **Internet Access** to fetch market data.
* ✅ Ensure **CORS is enabled** in Spring Boot for frontend-backend communication.
* 💡 You can enable Swagger for API documentation if needed.

---

## 👤 Author

**Sarra Chahab**

---
