# Booking API Automation Framework

A professional-grade API automation framework built using Java, Cucumber (BDD), and Rest-Assured, featuring modular design, structured logging, and seamless CI integration.

## ✅ Features
- 🟢 Create Booking API automated (other CRUD operations pending due to unavailable Swagger)
- 🧾 Field-level validation and error response checks for Create Booking
- 🪵 JSON logging via custom Rest-Assured filters with timestamps
- 🧩 Modular & scalable folder structure
- 📊 Default HTML Cucumber reports
- 📊 Rich custom HTML Cucumber reports
- ⚙️ CI-ready with Maven

## 🧰 Tech Stack
| Component         | Description                       |
|------------------|-----------------------------------|
| 🟦 Java 17        | Programming language              |
| 📦 Maven 3.9.9    | Build & dependency management     |
| 🧪 JUnit          | Test runner framework             |
| 🥒 Cucumber       | BDD test structure (Gherkin)      |
| 🔍 Rest Assured   | API testing library               |
| 📜 Gherkin        | Human-readable test scenarios     |

## 🚀 Getting Started

### 🧱 Prerequisites
- Java 17+
- Maven 3.9+
- IntelliJ IDEA (recommended)

### 🧩 IntelliJ Plugin Setup
- ✅ Cucumber for Java
- ✅ Gherkin

### 🛠️ Project Setup (IntelliJ)
1. Clone the repository:
   ```
   git clone https://github.com/narennatarajan92/hotel-booking-api/
   ```
2. Open in IntelliJ:
   - File → Open → Select the project folder → open as project
   - Select `pom.xml` if prompted
3. Set Project SDK:
   - File → Project Structure → Project SDK → Java 17
4. Enable Annotation Processing:
   - File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - ✅ Check "Enable annotation processing"

## 🧪 Running Tests

### 🔁 Option 1: Via Maven
```
mvn clean test
```

### 🔁 Option 2: From TestRunner class
- Open `TestRunner.java` and run directly from IntelliJ.
- Provide the tag you want to execute using the key `FILTER_TAGS_PROPERTY_NAME`.

#### Available Tags
- `@booking_regression` — All Create Booking scenarios (default)
- `@error_validation` — Error validation scenarios for Create Booking
- `@create_booking` — Create Booking API only

## 📂 Folder Structure Overview
```
src
├── main
│   └── java
│       └── com.POJO                    # POJO for serialization
├── test
│   ├── java
│   │   └── com.booking
│   │       ├── TestRunner              # Test runner
│   │       ├── utils                   # Utilities
│   │       └── stepDefinitions         # Step definition files
│   └── resources
│       ├── features                    # Gherkin feature files (only create_booking.feature implemented)
│       ├── schemas                     # Schemas for Create Booking
│       └── application.properties      # Properties

target
├── logs
│   └── logfile.txt                     # Custom JSON API logs
├── cucumber-reports-yyyyMMdd_HHmmss.html    # Main Cucumber HTML report
└── cucumber-html-report-yyyyMMdd_HHmmss     # Detailed HTML report folder
```

## 📊 Test Reports
After running the tests, two types of HTML reports are generated under the `target/` directory:

1️⃣ **Default Cucumber HTML Report**
- Location: `target/cucumber-reports-yyyyMMdd_HHmmss.html`
- How to open: Double-click or right-click → "Open with" → Select your browser

2️⃣ **Custom Cucumber HTML Report (Detailed View)**
- Location: `target/cucumber-html-report-yyyyMMdd_HHmmss/`
- How to open: Open the folder, locate and open `overview-features.html` in your browser

💡 *Tip: The timestamp (yyyyMMdd_HHmmss) in the file/folder name ensures every test run keeps its own separate reports — no overwriting.*

---

## 🛠 Logging: Custom Filter
- All API request & response logs are stored in `/target/logs/logfile.txt`
- Logged in **JSON format** with timestamps
- Works for **both success and failure cases**
- Logging is handled via a custom **Rest-Assured Filter**

---

## ⚠️ Troubleshooting
| Issue                       | Solution                                  |
|-----------------------------|-------------------------------------------|
| ❌ Tests not running         | Rebuild the project or check Java version |
| ❌ Missing logs              | Look carefully into target/logs folder    |
| ❌ Red annotations           | Enable annotation processing in IntelliJ  |
| ❌ Plugin warnings           | Install/update required IntelliJ plugins  |

---

## 👩‍💻 Author
Narenthiran Natarajan  
📁 [GitHub](https://github.com/narennatarajan92/hotel-booking-api/)

---
