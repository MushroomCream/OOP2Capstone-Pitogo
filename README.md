# Capstone-Pitogo: Gym Membership Management System
**Developed by:** Gabryle Antonie Pitogo

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
