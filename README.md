# OPPORTUNA
### Student Opportunity & Application Tracker

OPPORTUNA is a Java-based terminal application designed to help students keep track of internships, hackathons, scholarships and other opportunities in one place.

The main idea behind the project is not just to store opportunity details. It also helps a student decide which opportunities are more relevant to their current skills and deadlines.

For example, if an internship requires Java, SQL and Git, and the student already knows Java and SQL, OPPORTUNA shows the skill match and also points out Git as a missing skill.

---

## Why I Made This Project

Students usually find opportunities through different sources such as college groups, emails, social media and websites. Because of this, it is easy to lose track of deadlines or forget which opportunities have already been applied for.

I wanted to build something that could handle this problem using the Java concepts covered in the course.

The project therefore combines:

- Opportunity management
- Student skill management
- Skill matching
- Priority calculation
- Application tracking
- Deadline reminders
- File handling
- Database connectivity
- Object-oriented programming

---

## Main Features

### 1. Student Profile

The user can enter:

- Name
- Course
- Skills

The skills are stored and used later while checking opportunities.

### 2. Opportunity Management

The application can store opportunities such as:

- Internships
- Hackathons
- Scholarships
- Competitions

Each opportunity contains information such as its title, organization, required skills, deadline and stipend.

### 3. Skill Matching

OPPORTUNA compares the student's skills with the skills required by an opportunity.

The basic calculation is:

    Match Percentage =
    (Matched Skills / Required Skills) × 100

Skill names are normalized before comparison, so `Java`, `JAVA` and `java` are treated as the same skill.

The application also shows which skills are missing.

### 4. Opportunity Priority

The project does not simply display opportunities in the order in which they were added.

A priority score is calculated using multiple factors:

- Skill match
- Deadline urgency
- Stipend

The score is limited to 100.

This gives the student another way to decide which opportunities need attention first.

### 5. Application Tracking

A student can apply to an opportunity and track the application status.

Example statuses include:

- APPLIED
- SHORTLISTED
- INTERVIEW
- SELECTED
- REJECTED

The project also keeps a small history of status changes.

### 6. Duplicate Application Protection

Before adding a new application, the program checks whether an active application for the same opportunity already exists.

This prevents the same opportunity from being accidentally added multiple times.

### 7. Deadline Reminder

A separate worker checks opportunities that are approaching their deadlines.

This part uses Java multithreading so that the reminder task is handled separately from the main application logic.

### 8. Local Data Storage

The basic version stores information locally, which means the main application can be demonstrated without setting up a database.

Runtime files are created inside:

    data/

The files include:

    opportunities.dat
    applications.dat
    activity.log

### 9. JDBC and MySQL

The project also contains a JDBC implementation for storing opportunity and application data in MySQL.

This demonstrates:

- JDBC connection
- PreparedStatement
- INSERT
- SELECT
- UPDATE
- DELETE

### 10. JPA

A separate JPA/Hibernate implementation is included to demonstrate object-relational mapping using `EntityManager`.

---

# Technologies Used

- Java 17+
- Maven
- MySQL
- JDBC
- JPA / Hibernate
- Java Collections
- Java File I/O
- Multithreading

---

# Java Concepts Used

The project was designed around the Java topics covered in the course.

| Concept | Implementation |
|---|---|
| Classes & Objects | Models and service classes |
| Encapsulation | Private fields with getters/setters |
| Inheritance / OOP structure | Service and repository architecture |
| Interfaces | Repository interfaces |
| Packages | Separate packages for different responsibilities |
| Exception Handling | Validation and database operations |
| Custom Exceptions | `InvalidInputException`, `DatabaseOperationException` |
| ArrayList | Storing opportunities and applications |
| Vector | Skill processing |
| Stack | Application status history |
| HashMap | Match-score storage |
| TreeSet | Normalized skill sets |
| PriorityQueue | Opportunity prioritization |
| File I/O | Local data storage and activity log |
| Multithreading | Deadline reminder worker |
| Synchronization | Safe repository operations |
| Annotations | Custom `@Feature` annotation |
| Reflection | Finding annotated features at runtime |
| JDBC | MySQL database operations |
| JPA | ORM-based persistence |

---

# Project Structure

```text
OPPORTUNA/
│
├── pom.xml
├── README.md
├── PROJECT_REPORT.md
├── schema.sql
│
├── data/
│   └── generated when the application runs
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── opportuna/
        │           ├── annotation/
        │           ├── app/
        │           ├── exception/
        │           ├── model/
        │           ├── repository/
        │           │   ├── file/
        │           │   ├── jdbc/
        │           │   └── jpa/
        │           ├── service/
        │           ├── thread/
        │           └── util/
        │
        └── resources/
            └── META-INF/
                └── persistence.xml
