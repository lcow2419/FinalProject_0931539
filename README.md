# 🎬 Movie Catalogue System

A Spring Boot web application that lets users browse trending movies from [TMDb](https://www.themoviedb.org/), view detailed movie information, and manage a list of their favorite movies.

---

## 📌 Features

- 🔥 View **Trending Movies** from TMDb API  
- 🎥 View **Movie Details** fetched in real-time  
- ❤️ Add/Remove movies from your **Favorites**  
- ✅ Favorite state preserved in **H2 In-Memory Database**  
- 🌐 Thymeleaf-based UI  
- 🧪 Unit-tested Service Layer using JUnit & Mockito  

---

## 🛠️ Tech Stack

| Layer        | Technology            |
|--------------|------------------------|
| Backend      | Spring Boot (Java)     |
| UI           | Thymeleaf, HTML, CSS   |
| API Integration | TMDb REST API       |
| Database     | H2 In-Memory DB        |
| Testing      | JUnit 5, Mockito       |
| Build Tool   | Gradle (Kotlin DSL)    |

---

## ⚙️ Getting Started

### ✅ Prerequisites

- Java 17+
- Gradle 8+
- Internet connection (for TMDb API access)

### 🚀 Running the Application

1. **Clone the repository**

git clone https://github.com/your-username/movie-catalogue-system.git
cd movie-catalogue-system

2. **Build and Run**

./gradlew build
./gradlew bootRun

3. **Visit the app in browser**

http://localhost:8080