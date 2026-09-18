# OPPORTUNA 🚀

### Student Opportunity & Application Tracker

OPPORTUNA is a Java-based command-line project that I built to make it easier for students to keep track of internships, hackathons, scholarships, competitions, and other opportunities.

As students, we usually find opportunities in different places — college groups, WhatsApp/Telegram groups, emails, websites, LinkedIn, etc. Because of this, it is quite easy to forget an opportunity, miss a deadline, or lose track of an application.

So, I wanted to make a simple system where all of this could be managed from one place.

---

## 💡 What does OPPORTUNA do?

The basic idea is:

**Enter your skills → Check opportunities → See your skill match → Track applications → Don't miss deadlines**

For example, if I have:

```text
Java
Python
SQL
Git
```

and an internship requires:

```text
Java
SQL
Git
React
```

OPPORTUNA can tell me that I match **3 out of 4 skills** and that **React is a missing skill**.

It also considers the deadline and stipend while calculating the priority of an opportunity.

---

## ✨ Main Features

### 👤 Student Profile

The application starts by taking some basic information about the student:

* Name
* Course
* Skills

These skills are later used by the matching system.

---

### 📋 Manage Opportunities

I can add and manage different types of opportunities, for example:

* Internships
* Hackathons
* Scholarships
* Competitions

Each opportunity can contain:

* Opportunity title
* Organization
* Required skills
* Deadline
* Stipend

---

### 🎯 Skill Matching

One of the main parts of the project is the skill matching system.

It compares the skills entered by the student with the skills required by an opportunity.

The basic calculation is:

```text
Match Percentage =
(Matched Skills / Required Skills) × 100
```

It also shows which skills are missing.

For example:

```text
Your Skills:
Java, Python, SQL, Git

Required:
Java, SQL, Git, React

Match: 75%

Missing Skill:
React
```

I also normalize the skill names so that `Java`, `JAVA`, and `java` are treated as the same skill.

---

### ⭐ Opportunity Priority

Instead of simply showing opportunities in the order they were added, OPPORTUNA calculates a priority score.

The score considers:

* How well my skills match
* How close the deadline is
* The stipend offered

This gives a more useful idea of which opportunities I should look at first.

---

### 📝 Application Tracking

After finding an opportunity, I can save an application and update its status.

The available statuses are:

```text
SAVED
APPLIED
SHORTLISTED
INTERVIEW
SELECTED
REJECTED
```

The project also keeps a history of status changes.

So, instead of only knowing the current status, the application can keep track of how it reached that status.

---

### 🚫 Duplicate Application Check

The system checks whether an application already exists for an opportunity.

This prevents the same opportunity from accidentally being added multiple times.

---

### ⏰ Deadline Reminders

There is a separate reminder feature that checks upcoming deadlines.

I used Java multithreading for this part so that the reminder task can run separately from the main application flow.

---

### 💾 File Storage

The project can store data locally using Java File I/O.

This means the basic application does not require a database just to demonstrate the main functionality.

Runtime data is stored inside the:

```text
data/
```

folder.

---

### 🗄️ JDBC + MySQL

I also included a JDBC implementation to demonstrate database connectivity.

It covers basic database operations such as:

* Insert
* Read
* Update
* Delete

The SQL structure is provided in:

```text
schema.sql
```

---

### 🔗 JPA / Hibernate

The project also contains a separate JPA/Hibernate implementation.

This was included to demonstrate how Java objects can be mapped to database tables using ORM.

---

# 🧠 Java Concepts Used

I tried to use the Java concepts from the syllabus in an actual project instead of creating separate small programs for every topic.

| Concept            | How it is used                                        |
| ------------------ | ----------------------------------------------------- |
| Classes & Objects  | Used throughout the project                           |
| Encapsulation      | Private fields with getters/setters                   |
| Interfaces         | Repository interfaces                                 |
| Abstraction        | Repository/service architecture                       |
| Polymorphism       | Different repository implementations                  |
| Packages           | Code is divided into logical packages                 |
| Exception Handling | Handling invalid input and failures                   |
| Custom Exceptions  | `InvalidInputException`, `DatabaseOperationException` |
| ArrayList          | Storing collections of objects                        |
| Vector             | Skill-related data handling                           |
| Stack              | Application status history                            |
| HashMap            | Storing match-related information                     |
| TreeSet            | Managing unique/normalized skills                     |
| PriorityQueue      | Handling prioritized opportunities                    |
| File I/O           | Saving and loading local data                         |
| Multithreading     | Deadline reminder worker                              |
| Synchronization    | Safe access to shared data                            |
| Annotations        | Custom `@Feature` annotation                          |
| Reflection         | Detecting annotated methods                           |
| JDBC               | MySQL database operations                             |
| JPA                | ORM-based database operations                         |

---

# 🔄 How the Project Works

The overall flow is:

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

The project is intentionally command-line based because the submission is supposed to be executable through the terminal.

The main menu looks like:

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
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── opportuna/
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

* **Java**
* **Maven**
* **MySQL**
* **JDBC**
* **JPA / Hibernate**
* **Java Collections**
* **File Handling**
* **Multithreading**

---

# ▶️ How to Run

### 1. Clone the repository

```bash
git clone https://github.com/saumyadixit3006/Opportuna.git
```

### 2. Open the project

```bash
cd Opportuna
```

### 3. Compile

```bash
mvn clean compile
```

### 4. Run

```bash
mvn exec:java
```

The application will start directly in the terminal.

---

# 🗄️ Database Setup

The main application can work with local file storage.

If you want to test the MySQL/JDBC part:

1. Install MySQL.
2. Create the required database.
3. Run the commands present in `schema.sql`.
4. Configure the database connection.
5. Run the JDBC-related functionality.

The JPA implementation can be tested separately as well.

---

# 📌 Example

Suppose I enter:

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

OPPORTUNA will show something similar to:

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

The system can then use this along with the deadline and stipend to calculate the opportunity's priority.

---

# 🔍 What makes the project different from simple CRUD?

The project is not only about adding, deleting, or updating records.

There is some actual decision logic involved.

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

So the project uses the stored data to generate useful information for the student.

---

# ⚠️ Error Handling

The project includes exception handling for situations such as:

* Invalid input
* Invalid opportunity details
* Duplicate applications
* Database errors
* File-related errors

Custom exceptions used include:

```text
InvalidInputException
DatabaseOperationException
```

---

# 🧵 Multithreading

The deadline reminder feature uses Java's concurrency utilities.

A separate worker is responsible for checking upcoming deadlines.

This gave me a practical way to use multithreading instead of only demonstrating threads with a basic example.

---

# 🔎 Annotations & Reflection

The project contains a custom annotation:

```java
@Feature
```

Reflection is then used to inspect classes and find methods containing this annotation.

This demonstrates how Java can inspect program information at runtime.

---

# 🚧 Current Limitations

The current version is mainly designed as a Java command-line project.

Some things that are not implemented yet:

* Online opportunity fetching
* Login/authentication
* Email notifications
* Web interface
* Mobile application
* AI-based resume analysis
* Automatic opportunity collection from websites

These can be added in future versions.

---

# 🔮 Future Scope

Some ideas I would like to add later are:

* Resume-based skill extraction
* Automatic opportunity collection
* Email/deadline notifications
* Better recommendation algorithms
* Web dashboard
* User login
* Cloud database
* Analytics for applications
* AI-assisted opportunity matching

---

# 📚 What I Learned

While making this project, I got practical experience with:

* Designing classes and packages
* Working with Java collections
* Using interfaces and abstraction
* Handling exceptions
* Working with files
* Using threads
* Working with JDBC
* Understanding JPA/Hibernate
* Using annotations and reflection
* Structuring a Maven project
* Managing a project using Git and GitHub


# 👩‍💻 Author

**Saumya Dixit**

B.Tech CSE (AIML)
VIT Bhopal University

GitHub:
https://github.com/saumyadixit3006
