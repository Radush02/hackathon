# Real Estate API

This project implements a filtering API as a challenge for the Stone Soup Tech hackathon.

## Prerequisites

- Java 21 (or higher)
- Maven 3.9+
- MySQL 8.0+

## Database setup

1.  **Create Database:**
    ```sql
    CREATE DATABASE stonesoup;
    ```
2.  **Import Data:**
    Run the following command in your terminal (enter password when prompted):
    ```bash
    mysql -u your_username -p stonesoup < 1k_dump.sql
    ```
3.  **Environment Setup:**
    * Copy `.env.example` to a new file named `.env`.
    * Update the values in `.env` with your local MySQL credentials.

---

##  Running the Application

Ensure your environment variables are loaded, then run:
```bash
./mvnw spring-boot:run
```
The API will start on `http://localhost:8080`.

---

##  API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/listings/health` | Returns `{"status":"ok"}`. |
| **GET** | `/listings` | Search with filters. Results sorted by ID. |
| **GET** | `/listings/{listing_id}` | Returns full details (includes title/description). |

---