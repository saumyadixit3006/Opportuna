# OPPORTUNA 🚀
### Student Opportunity & Application Tracker

OPPORTUNA is a Java-based command-line application designed to help students manage internships, hackathons, scholarships, competitions, and other opportunities from one place.

Students often find opportunities through different sources such as college groups, WhatsApp/Telegram groups, emails, websites, LinkedIn, and other platforms. Because of this, it is easy to forget an opportunity, miss a deadline, or lose track of an application.

OPPORTUNA brings these tasks together into a single system.

> **Enter your skills → Check opportunities → Calculate skill match → Track applications → Monitor deadlines**

---

## 💡 What Does OPPORTUNA Do?

The system allows a student to:

- Create a student profile
- Enter and manage skills
- View available opportunities
- Add new opportunities
- Compare student skills with required skills
- Calculate skill match percentage
- Identify missing skills
- Calculate opportunity priority
- Consider deadline urgency and stipend
- Apply to opportunities
- Track application status
- Maintain application status history
- Prevent duplicate applications
- Check upcoming deadlines
- Store data locally using Java File I/O
- Demonstrate JDBC and MySQL operations
- Demonstrate JPA/Hibernate ORM
- Use Java collections, exceptions, multithreading, annotations, and reflection

---

# ✨ Main Features

## 👤 1. Student Profile

The application starts by collecting basic information about the student.

The profile contains:

- Name
- Course
- Skills

The student's skills are later used by the opportunity matching system.

---

## 📋 2. Opportunity Management

OPPORTUNA allows different types of opportunities to be managed, such as:

- Internships
- Hackathons
- Scholarships
- Competitions

Each opportunity can contain:

- Opportunity title
- Organization
- Opportunity type
- Required skills
- Deadline
- Duration
- Stipend

---

## 🎯 3. Skill Matching

One of the main features of OPPORTUNA is its skill matching system.

The system compares the skills entered by the student with the skills required by an opportunity.

### Match Formula

```text
Match Percentage =
(Matched Skills / Required Skills) × 100
```

### Example

Student skills:

```text
Java
Python
SQL
Git
```

Required skills:

```text
Java
SQL
Git
React
```

Result:

```text
Matched Skills:
Java
SQL
Git

Missing Skills:
React

Match Percentage:
75%
```

The system also normalizes skill names, meaning:

```text
Java
JAVA
java
```

are treated as the same skill.

---

## ⭐ 4. Opportunity Priority

OPPORTUNA does more than simply display opportunities.

It calculates an opportunity priority based on factors such as:

- Skill match
- Deadline urgency
- Stipend

The purpose is to help the student identify opportunities that deserve attention.

The overall decision flow is:

```text
Student Skills
      ↓
Skill Comparison
      ↓
Match Percentage
      ↓
Missing Skills
      ↓
Deadline Analysis
      ↓
Stipend
      ↓
Priority Score
```

---

## 📝 5. Application Tracking

After finding an opportunity, the student can apply and track the application.

Available application statuses are:

```text
SAVED
APPLIED
SHORTLISTED
INTERVIEW
SELECTED
REJECTED
```

The project also maintains a history of application status changes.

This means the system can track how an application moved from one status to another instead of storing only the current status.

---

## 🚫 6. Duplicate Application Protection

OPPORTUNA checks whether an application already exists for an opportunity.

This prevents the same opportunity from accidentally being added multiple times.

---

## ⏰ 7. Deadline Reminders

The project contains a deadline reminder feature that checks upcoming opportunity deadlines.

A separate worker is used for this functionality so that deadline checking can operate independently from the main application flow.

Java multithreading and concurrency concepts are used for this feature.

---

## 💾 8. Local File Storage

The main application supports local data storage using Java File I/O.

This means the basic application can run without requiring a database.

Runtime data is stored inside:

```text
data/
```

This makes the basic project easier to demonstrate and execute.

---

## 🗄️ 9. JDBC + MySQL

OPPORTUNA also contains a JDBC implementation for demonstrating database connectivity.

The JDBC implementation covers basic database operations:

- Insert
- Read
- Update
- Delete

The database structure is provided in:

```text
schema.sql
```

The JDBC functionality is separate from the basic local file-storage flow.

---

## 🔗 10. JPA / Hibernate

The project also contains a separate JPA/Hibernate implementation.

This demonstrates Object-Relational Mapping (ORM), where Java objects can be mapped to database tables.

The project uses:

- JPA
- Hibernate
- Persistence configuration
- Entity classes

---

# 🧠 Java Concepts Used

The project applies Java concepts from the syllabus in a single practical application.

| Java Concept | How It Is Used |
|---|---|
| Classes & Objects | Used throughout the project |
| Encapsulation | Private fields with getters/setters |
| Interfaces | Repository interfaces |
| Abstraction | Repository and service architecture |
| Polymorphism | Different repository implementations |
| Packages | Code is divided into logical packages |
| Exception Handling | Handling invalid input and failures |
| Custom Exceptions | `InvalidInputException`, `DatabaseOperationException` |
| ArrayList | Storing collections of objects |
| Vector | Skill-related data handling |
| Stack | Application status history |
| HashMap | Storing match-related information |
| TreeSet | Managing unique/normalized skills |
| PriorityQueue | Handling prioritized opportunities |
| File I/O | Saving and loading local data |
| Multithreading | Deadline reminder worker |
| Synchronization | Safe access to shared data |
| Annotations | Custom `@Feature` annotation |
| Reflection | Detecting annotated methods |
| JDBC | MySQL database operations |
| JPA | ORM-based database operations |

---

# 🔄 How the Project Works

The overall workflow of OPPORTUNA is:

```text
Start
  ↓
Create Student Profile
  ↓
Enter Skills
  ↓
View Opportunities
  ↓
Compare Skills
  ↓
Calculate Match %
  ↓
Find Missing Skills
  ↓
Check Deadline & Stipend
  ↓
Calculate Priority
  ↓
Apply
  ↓
Track Application Status
  ↓
Check Reminders
  ↓
Exit
```

---

# 🖥️ Command-Line Menu

OPPORTUNA is intentionally command-line based so that it can be executed directly through the terminal.

The main menu contains:

```text
1. View opportunities
2. Get skill + deadline recommendations
3. Add an opportunity
4. Apply to an opportunity
5. View applications
6. Update application status
7. Check deadline reminders
8. Show technical features
9. Delete an opportunity
0. Exit
```

---

# 📁 Project Structure

```text
OPPORTUNA/
│
├── .gitignore
├── README.md
├── PROJECT_REPORT.md
├── pom.xml
├── schema.sql
├── run.bat
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── opportuna/
        │           │
        │           ├── annotation/
        │           │   └── Feature.java
        │           │
        │           ├── app/
        │           │   └── Main.java
        │           │
        │           ├── exception/
        │           │   ├── DatabaseOperationException.java
        │           │   └── InvalidInputException.java
        │           │
        │           ├── model/
        │           │   ├── Application.java
        │           │   ├── ApplicationStatus.java
        │           │   ├── Opportunity.java
        │           │   └── StudentProfile.java
        │           │
        │           ├── repository/
        │           │   ├── ApplicationRepository.java
        │           │   ├── OpportunityRepository.java
        │           │
        │           │   ├── file/
        │           │   ├── jdbc/
        │           │   └── jpa/
        │           │
        │           ├── service/
        │           │   ├── MatchEngine.java
        │           │   ├── OpportunityService.java
        │           │   └── PriorityEngine.java
        │           │
        │           ├── thread/
        │           │   └── ReminderWorker.java
        │           │
        │           └── util/
        │               ├── ActivityLog.java
        │               ├── FileStore.java
        │               ├── InputValidator.java
        │               └── ReflectionCatalog.java
        │
        └── resources/
            └── META-INF/
                └── persistence.xml
```

---

# 🛠️ Technologies Used

### Programming & Build

- Java 17+
- Maven

### Database

- MySQL
- JDBC
- JPA
- Hibernate

### Java Concepts

- Collections
- File I/O
- Exception Handling
- Multithreading
- Synchronization
- Annotations
- Reflection

### Version Control

- Git
- GitHub

---

# ▶️ How to Run

## Requirements

Before running OPPORTUNA, make sure you have:

- Java 17 or higher
- Apache Maven 3.8+
- Git (only required if cloning the repository)

You can verify Java and Maven using:

```bash
java -version
mvn -version
```

---

## 1. Clone the Repository

```bash
git clone https://github.com/saumyadixit3006/Opportuna.git
```

---

## 2. Open the Project

```bash
cd Opportuna
```

---

## 3. Compile the Project

```bash
mvn clean compile
```

If the compilation is successful, Maven will display:

```text
BUILD SUCCESS
```

---

## 4. Run the Application

```bash
mvn exec:java
```

The application will start directly in the terminal.

---

## 🪟 Windows Quick Run

Windows users can use the included:

```text
run.bat
```

Double-clicking `run.bat` will:

1. Compile the project
2. Check whether the build succeeds
3. Start OPPORTUNA

It can also be executed from Command Prompt:

```cmd
run.bat
```

---

# 🗄️ Database Setup

The main application uses local file storage for its basic functionality.

Therefore, **MySQL is not required for the normal project demonstration**.

If you want to test the JDBC/MySQL functionality:

1. Install MySQL.
2. Create the required database.
3. Run the SQL commands provided in `schema.sql`.
4. Configure the database connection.
5. Run the JDBC-related functionality.

The JPA/Hibernate implementation can also be tested separately.

---

# 📌 Example

Suppose the student enters:

```text
My Skills:
Java
Python
SQL
Git
```

An opportunity requires:

```text
Required Skills:
Java
SQL
Git
React
```

OPPORTUNA identifies:

```text
Matched Skills:
Java
SQL
Git

Missing Skills:
React

Match Percentage:
75%
```

The system can then use the skill match together with deadline and stipend information to calculate the opportunity priority.

---

# 🔍 What Makes OPPORTUNA More Than Simple CRUD?

The project is not limited to adding, deleting, and updating records.

It contains actual application logic that processes stored information and generates useful results.

For example:

```text
Student Skills
      ↓
Skill Comparison
      ↓
Match Percentage
      ↓
Missing Skills
      ↓
Deadline Analysis
      ↓
Stipend
      ↓
Priority Score
```

This gives the project a meaningful problem-solving component in addition to CRUD operations.

---

# ⚠️ Error Handling

The project includes exception handling for situations such as:

- Invalid input
- Invalid opportunity details
- Duplicate applications
- Database errors
- File-related errors

Custom exceptions include:

```text
InvalidInputException
DatabaseOperationException
```

These exceptions help separate different types of application failures and make error handling more structured.

---

# 🧵 Multithreading

The deadline reminder functionality uses Java concurrency concepts.

A separate worker is responsible for checking upcoming deadlines.

This provides a practical use of multithreading instead of using threads only in an isolated demonstration program.

---

# 🔎 Annotations & Reflection

The project contains a custom annotation:

```java
@Feature
```

Reflection is used to inspect classes and identify methods containing this annotation.

This demonstrates how Java can inspect program information at runtime.

---

# 🏗️ Architecture

The project follows a layered structure to keep different responsibilities separated.

```text
                OPPORTUNA
                    │
                    ▼
              Application Layer
                    │
                    ▼
               Service Layer
                    │
          ┌─────────┼─────────┐
          ▼         ▼         ▼
      Matching   Priority   Reminder
                    │
                    ▼
             Repository Layer
          ┌─────────┼─────────┐
          ▼         ▼         ▼
        File       JDBC       JPA
          │         │         │
          ▼         ▼         ▼
       Storage   MySQL    Hibernate
```

This structure makes the project easier to organize and extend.

---

# 🚧 Current Limitations

The current version is mainly designed as a Java command-line project.

The following features are not implemented yet:

- Online opportunity fetching
- Login/authentication
- Email notifications
- Web interface
- Mobile application
- AI-based resume analysis
- Automatic opportunity collection from websites

These can be considered for future versions.

---

# 🔮 Future Scope

Possible future improvements include:

- Resume-based skill extraction
- Automatic opportunity collection
- Email and deadline notifications
- Improved recommendation algorithms
- Web dashboard
- User login
- Cloud database
- Application analytics
- AI-assisted opportunity matching

---

# 📚 What I Learned

While developing OPPORTUNA, I gained practical experience with:

- Designing Java classes and packages
- Object-oriented programming
- Java collections
- Interfaces and abstraction
- Exception handling
- File handling
- Multithreading
- Synchronization
- JDBC
- JPA/Hibernate
- Annotations
- Reflection
- Maven project structure
- Git and GitHub
- Building a complete Java application around a real-world problem

---

# 🎯 Project Objective

The main objective of OPPORTUNA is to create a centralized system that helps students organize opportunities, understand how well their skills match those opportunities, identify missing skills, prioritize opportunities, and track their applications.

Instead of maintaining separate notes or spreadsheets, the student can manage the complete opportunity lifecycle inside one application.

```text
Discover
   ↓
Compare
   ↓
Prioritize
   ↓
Apply
   ↓
Track
   ↓
Complete
```

---

# 👩‍💻 Author

**Saumya Dixit**

B.Tech CSE (AIML)  
VIT Bhopal University

GitHub:

https://github.com/saumyadixit3006

---

# 📄 License

This project is developed as an academic and learning project.
