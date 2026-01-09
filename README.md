# NexusFlow (Java Edition)

**A high-performance task orchestrator built with Java 21 and Spring Boot 4.** This implementation leverages Virtual Threads for superior scalability, PostgreSQL for state durability, and Gradle for advanced build automation.



## 🚀 Modern Features
* **Java 21 Virtual Threads:** Handles massive concurrent I/O with minimal memory.
* **Spring Boot 4 Architecture:** Utilizes modular auto-configuration and native API versioning.
* **DAG Engine:** Manages complex task dependencies and execution sequences.
* **ACID Persistence:** Transactional state tracking via Jakarta Persistence 3.2.

## 🛠️ Tech Stack
* **Language:** Java 21 (LTS)
* **Framework:** Spring Boot 4.0.1
* **Build Tool:** Gradle 9.x
* **Messaging:** RabbitMQ (Spring AMQP 4.0)

## 🏁 Getting Started
1. Clone the repo: `git clone https://github.com/your-username/nexusflow-java-edition.git`
2. Ensure you have JDK 21 installed: `java -version`
3. Build the project: `./gradlew build`
4. Run the application: `./gradlew bootRun`
