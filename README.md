# Gym Membership Management System (OOP2 Capstone - Pitogo)

A robust desktop application built using **JavaFX** and **JDBC** to manage gym memberships, member check-ins, payment transactions, and administrative oversight.

---

## 🛠️ Architecture & Core Features

*   **Role-Based Access Control:** Separate dashboards and permissions for **Members** and **Staff/Administrators**.
*   **Database Integration:** Fully connected to a relational MySQL database via JDBC (`gymmembership`).
*   **Session Persistence (Java Serialization):** Utilizes `java.io.Serializable` to manage active user sessions securely via a local binary file (`session.dat`), ensuring session persistence across app states and automatic cleanup upon logout.

---

## 📐 Implemented Design Patterns & Principles

To adhere to high software engineering standards and satisfy capstone requirements, this project integrates key Object-Oriented Design Patterns and SOLID principles:

### 1. Creational Pattern: **Singleton Pattern**
*   **Implementation:** `MySQLConnection`
*   **Purpose:** Ensures that only a single instance of the database connection manager is created throughout the application's lifecycle, optimizing memory usage and preventing connection leaks.

### 2. Structural Pattern: **Facade Pattern**
*   **Implementation:** `GymDatabaseFacade`
*   **Purpose:** Wraps complex JDBC and raw SQL execution logic (`java.sql.*`) behind a clean, simplified interface. This decouples the JavaFX UI controllers from the backend database subsystem.

### 3. Behavioral Pattern: **Strategy Pattern**
*   **Implementation:** `PricingStrategy` (with `RegularPricing` and `StudentDiscountPricing`)
*   **Purpose:** Encapsulates different pricing algorithms and makes them interchangeable at runtime. This allows dynamic calculation of subscription fees (e.g., standard rates vs. student discounts) without cluttering controller code.

### 4. Applied SOLID Principles
*   **Single Responsibility Principle (SRP):** Controllers handle only UI events, the Facade handles database communication, and Strategy classes handle specific calculations.
*   **Open/Closed Principle (OCP):** New pricing behaviors (e.g., Holiday Discounts) can be added by creating new strategy classes without modifying existing controller or facade code.

---

## 📊 System Diagrams

### Class Diagram Highlights
*   **Data Models:** `User`, `AttendanceRecord`, `MembershipPlan`, `Payment`
*   **Controllers:** `LoginController`, `MemberDashboardController`, `StaffDashboardController`, `MemberPlansController`, etc.
*   **Utilities:** `MySQLConnection` (Singleton) and `UserSession` (`<<Serializable>>`).

### Sequence & Activity Workflow
*   **Session Management:** Verifies identity via deserialized `session.dat` state.
*   **Transaction Pipeline:** Triggered via UI $\rightarrow$ Strategy Pattern calculates final pricing $\rightarrow$ Facade Pattern processes transaction $\rightarrow$ Singleton connection executes SQL query.

---

## 🚀 Getting Started

1. **Prerequisites:** 
   * Java Development Kit (JDK 17 or higher)
   * JavaFX SDK
   * MySQL Server (XAMPP or local instance) with database name `gymmembership`
2. **Database Setup:** 
   * Import the project SQL schema into your local MySQL instance.
3. **Execution:** 
   * Run the application through your IDE or build tool (Maven/Gradle) starting from the main application entry point.
## Software System Overview
A desktop-based JavaFX and MySQL application designed to streamline gym operations. It features distinct workflows for **Members** (checking in, viewing available membership plans, selecting subscriptions, and monitoring payment history) and **Staff** (managing user records, reviewing attendance tracking logs, checking payment statuses, and maintaining membership options).

## Major Features
* **Role-Based Authentication:** Secure login routing separating Staff and Member access permissions.
* **Attendance Tracking:** Real-time member check-in system mapped to foreign key constraints and dynamic timestamps.
* **Membership Plan & Payment Processing:** Dynamic plan creation by staff and transaction logging for members.
* **Java Serialization Session Management:** Persistent binary user state management file tracking active sessions.

## Java Serialization Mechanism
To satisfy session requirements, the application utilizes **Java Object Serialization**.
* Upon successful authentication, a binary session data file (`session.dat`) is dynamically generated containing the user's `userId` and `role`.
* The system reads from this file (`ObjectInputStream`) to validate permissions across navigation steps.
* Upon triggering a logout event, the file is automatically purged from the directory using standard file deletion mechanisms (`file.delete()`).

## Applied SOLID Design Principles
1. **Single Responsibility Principle (SRP):**
    * *Classes Involved:* `MySQLConnection.java` and various controllers (`LoginController.java`, `AttendanceController.java`).
    * *Benefit:* Database handling logic is completely isolated from user interface event handling, ensuring cleaner code maintenance and separation of concerns.
2. **Dependency Inversion Principle (DIP):**
    * *Classes Involved:* TableView components utilizing decoupled data transfer models (e.g., `PlansController.Plan`, `AttendanceController.Record`).
    * *Benefit:* UI components interact with structured data abstractions, minimizing tight coupling and making component extensions modular.
