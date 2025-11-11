# 🏗️ Architecture & Design Documentation

## System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        BROWSER                               │
│                   (User Interface)                           │
└──────────────────────┬──────────────────────────────────────┘
                       │ HTTP Requests
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                 SPRING BOOT APPLICATION                      │
│                                                               │
│  ┌───────────────────────────────────────────────────────┐  │
│  │           PRESENTATION LAYER (Views)                  │  │
│  │              Thymeleaf Templates                       │  │
│  │  • admin-dashboard.html  • register-student.html      │  │
│  │  • students-list.html    • add-room.html              │  │
│  │  • rooms-list.html       • allocate-room.html         │  │
│  │  • submit-complaint.html • complaints-list.html       │  │
│  └───────────────────────┬───────────────────────────────┘  │
│                          │                                   │
│  ┌───────────────────────▼───────────────────────────────┐  │
│  │           CONTROLLER LAYER (MVC)                      │  │
│  │           @Controller classes                          │  │
│  │                                                         │  │
│  │  DashboardController    StudentController             │  │
│  │  RoomController         AllocationController          │  │
│  │  ComplaintController                                  │  │
│  │                                                         │  │
│  │  • Handle HTTP Requests (GET/POST)                    │  │
│  │  • Business Logic                                     │  │
│  │  • Model manipulation                                 │  │
│  └───────────────────────┬───────────────────────────────┘  │
│                          │                                   │
│  ┌───────────────────────▼───────────────────────────────┐  │
│  │         REPOSITORY LAYER (Data Access)               │  │
│  │         Spring Data JPA Repositories                  │  │
│  │                                                         │  │
│  │  StudentRepository                                    │  │
│  │  RoomRepository                                       │  │
│  │  ComplaintRepository                                  │  │
│  │                                                         │  │
│  │  • CRUD Operations                                    │  │
│  │  • Custom Queries                                     │  │
│  └───────────────────────┬───────────────────────────────┘  │
│                          │                                   │
│  ┌───────────────────────▼───────────────────────────────┐  │
│  │           MODEL LAYER (Entities)                      │  │
│  │           JPA Entities                                 │  │
│  │                                                         │  │
│  │  Student (@Entity)                                    │  │
│  │  Room (@Entity)                                       │  │
│  │  Complaint (@Entity)                                  │  │
│  │                                                         │  │
│  │  • Database table mappings                            │  │
│  │  • Entity relationships                               │  │
│  └───────────────────────┬───────────────────────────────┘  │
│                          │                                   │
└──────────────────────────┼───────────────────────────────────┘
                           │ JDBC
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              H2 IN-MEMORY DATABASE                           │
│              jdbc:h2:mem:hosteldb                            │
│                                                               │
│  Tables: STUDENT, ROOM, COMPLAINT                            │
└─────────────────────────────────────────────────────────────┘
```

## MVC Pattern Implementation

```
┌──────────────┐         ┌──────────────┐         ┌──────────────┐
│              │         │              │         │              │
│    MODEL     │◄────────│  CONTROLLER  │────────►│     VIEW     │
│              │         │              │         │              │
│  (Entities)  │         │ (Business    │         │ (Thymeleaf)  │
│  Student     │         │  Logic)      │         │  HTML Pages  │
│  Room        │         │              │         │              │
│  Complaint   │         │              │         │              │
│              │         │              │         │              │
└──────┬───────┘         └──────┬───────┘         └──────────────┘
       │                        │
       │                        │
       ▼                        ▼
┌──────────────┐         ┌──────────────┐
│ REPOSITORY   │         │    HTTP      │
│ (Data Access)│         │  Requests    │
└──────────────┘         └──────────────┘
```

## Entity Relationship Diagram

```
┌─────────────────────────┐
│       STUDENT           │
├─────────────────────────┤
│ • id (PK)               │
│ • fullName              │
│ • email                 │
│ • phoneNumber           │
│ • guardianName          │
│ • room_id (FK) ────┐    │
└─────────────────────┘   │
                          │
                          │ @OneToOne
                          │
                          ▼
                    ┌─────────────────────────┐
                    │       ROOM              │
                    ├─────────────────────────┤
                    │ • id (PK)               │
                    │ • roomNumber            │
                    │ • status                │
                    │   (Vacant/Occupied)     │
                    └─────────────────────────┘


┌─────────────────────────┐
│      COMPLAINT          │
├─────────────────────────┤
│ • id (PK)               │
│ • studentName           │
│ • roomNumber            │
│ • complaintDetails      │
└─────────────────────────┘
(No relationship - standalone)
```

## Request Flow Diagram

### Example: Allocating a Room to a Student

```
1. USER ACTION
   │
   └─► Browser: Click "Allocate Room" link
       │
       │ GET /allocate
       ▼
2. CONTROLLER
   │
   └─► AllocationController.showAllocationForm()
       │
       ├─► StudentRepository.findByRoomIsNull()
       │   └─► Returns: List<Student> (unassigned)
       │
       ├─► RoomRepository.findByStatus("Vacant")
       │   └─► Returns: List<Room> (vacant)
       │
       └─► model.addAttribute("students", unassignedStudents)
           model.addAttribute("rooms", vacantRooms)
           return "allocate-room"
           │
           ▼
3. VIEW (Thymeleaf)
   │
   └─► Renders allocate-room.html
       • Populates student dropdown
       • Populates room dropdown
       │
       ▼
4. USER SELECTS & SUBMITS
   │
   └─► Browser: POST /allocate
       Data: studentId=1, roomId=2
       │
       ▼
5. CONTROLLER
   │
   └─► AllocationController.allocateRoom(studentId, roomId)
       │
       ├─► StudentRepository.findById(studentId)
       ├─► RoomRepository.findById(roomId)
       │
       ├─► student.setRoom(room)
       ├─► room.setStatus("Occupied")
       │
       ├─► StudentRepository.save(student)
       ├─► RoomRepository.save(room)
       │
       └─► return "redirect:/allocate"
           │
           ▼
6. BROWSER
   │
   └─► Redirects to GET /allocate
       (Shows updated lists)
```

## URL Routing Map

```
Homepage: /
    │
    ├─► /students/new ─────► GET: Show Form ───► POST /students ───► Redirect to /students
    │                                                                         │
    │                                                                         ▼
    ├─► /students ─────────────────────────────────────────────► Display all students
    │
    ├─► /rooms/new ────────► GET: Show Form ───► POST /rooms ───► Redirect to /rooms
    │                                                                    │
    │                                                                    ▼
    ├─► /rooms ────────────────────────────────────────────► Display all rooms
    │
    ├─► /allocate ─────────► GET: Show Form ───► POST /allocate ──► Redirect to /allocate
    │                        (with dropdowns)    (assign room)            │
    │                                                                     ▼
    │                                                        Updated allocation view
    │
    ├─► /complaint ────────► GET: Show Form ───► POST /complaint ──► Redirect to /complaint?success
    │                                                                         │
    │                                                                         ▼
    │                                                              Show success message
    │
    └─► /admin/complaints ───────────────────────────────► Display all complaints
```

## Data Flow: Student Registration

```
┌──────────────┐
│   Browser    │
│              │
│ Fill Form    │
│ Click Submit │
└──────┬───────┘
       │
       │ POST /students
       │ {fullName, email, phoneNumber, guardianName}
       ▼
┌──────────────────────────┐
│  StudentController       │
│  registerStudent()       │
│                          │
│  Student student ────────┼──► Spring MVC binds form data
│                          │    to Student object
└──────┬───────────────────┘
       │
       │ studentRepository.save(student)
       ▼
┌──────────────────────────┐
│  StudentRepository       │
│  extends JpaRepository   │
│                          │
│  save(student)           │
└──────┬───────────────────┘
       │
       │ JPA/Hibernate
       ▼
┌──────────────────────────┐
│  H2 Database             │
│                          │
│  INSERT INTO student     │
│  VALUES (...)            │
└──────┬───────────────────┘
       │
       │ Success
       ▼
┌──────────────────────────┐
│  StudentController       │
│                          │
│  return "redirect:       │
│         /students"       │
└──────┬───────────────────┘
       │
       │ HTTP 302 Redirect
       ▼
┌──────────────────────────┐
│  Browser                 │
│                          │
│  GET /students           │
│  (Show updated list)     │
└──────────────────────────┘
```

## Technology Stack Details

```
┌─────────────────────────────────────────────────────────┐
│                    APPLICATION LAYER                     │
├─────────────────────────────────────────────────────────┤
│  Spring Boot 3.5.7                                      │
│  • Auto-configuration                                   │
│  • Embedded Tomcat server                               │
│  • Dependency management                                │
└─────────────────────────────────────────────────────────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
        ▼                 ▼                 ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│  Spring MVC  │  │  Spring Data │  │  Thymeleaf   │
│              │  │  JPA         │  │              │
│  • @Controller│  │              │  │  • Templates │
│  • @GetMapping│  │  • Repository│  │  • th:*      │
│  • @PostMapping│ │  • Entities  │  │  • th:each   │
│  • Model     │  │  • Queries   │  │  • th:object │
└──────────────┘  └──────┬───────┘  └──────────────┘
                         │
                         ▼
                  ┌──────────────┐
                  │  Hibernate   │
                  │  (JPA Impl)  │
                  │              │
                  │  • ORM       │
                  │  • DDL auto  │
                  └──────┬───────┘
                         │
                         ▼
                  ┌──────────────┐
                  │  H2 Database │
                  │  (In-Memory) │
                  │              │
                  │  • JDBC      │
                  │  • Console   │
                  └──────────────┘
```

## Package Structure

```
com.hostelmanagement.hostel
│
├── HostelApplication.java (Main @SpringBootApplication)
│
├── controller/
│   ├── DashboardController.java
│   ├── StudentController.java
│   ├── RoomController.java
│   ├── AllocationController.java
│   └── ComplaintController.java
│
├── model/
│   ├── Student.java (@Entity)
│   ├── Room.java (@Entity)
│   └── Complaint.java (@Entity)
│
└── repository/
    ├── StudentRepository.java (extends JpaRepository)
    ├── RoomRepository.java (extends JpaRepository)
    └── ComplaintRepository.java (extends JpaRepository)
```

## Key Design Patterns Used

### 1. MVC (Model-View-Controller)
- **Model**: JPA Entities (Student, Room, Complaint)
- **View**: Thymeleaf HTML templates
- **Controller**: Spring MVC Controllers

### 2. Repository Pattern
- Abstraction over data access
- Spring Data JPA provides implementation
- Custom queries using method naming conventions

### 3. Dependency Injection
- `@Autowired` for repository injection
- Spring manages object lifecycle

### 4. Front Controller Pattern
- DispatcherServlet handles all requests
- Routes to appropriate controllers

## State Transitions

### Room Status State Machine
```
┌─────────┐
│ VACANT  │
└────┬────┘
     │
     │ When room allocated to student
     │ (POST /allocate)
     ▼
┌─────────┐
│OCCUPIED │
└─────────┘
```

### Student Room Assignment Flow
```
┌───────────────┐
│ No Room       │
│ (room = null) │
└───────┬───────┘
        │
        │ When room allocated
        │ (AllocationController)
        ▼
┌───────────────┐
│ Has Room      │
│ (room = Room) │
└───────────────┘
```

## Configuration Files

### application.properties
```properties
# Application Name
spring.application.name=hostel

# H2 Database
spring.datasource.url=jdbc:h2:mem:hosteldb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### pom.xml (Key Dependencies)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## Deployment & Execution

```
┌──────────────┐
│ Maven Build  │
│ mvnw clean   │
│ install      │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Spring Boot  │
│ Application  │
│ Startup      │
└──────┬───────┘
       │
       ├─► Initialize Spring Context
       ├─► Configure DataSource (H2)
       ├─► Create JPA Entities (DDL)
       ├─► Register Controllers
       ├─► Load Thymeleaf Templates
       ├─► Start Embedded Tomcat
       │
       ▼
┌──────────────┐
│ Application  │
│ Running on   │
│ Port 8080    │
└──────────────┘
```

---

This architecture demonstrates a clean separation of concerns following the MVC pattern with Spring Boot best practices.

