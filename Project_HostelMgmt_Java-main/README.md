# 🏢 Student Hostel Management System (SHMS)

A lightweight, web-based Student Hostel Management System built with Spring Boot, demonstrating the MVC (Model-View-Controller) pattern with fundamental CRUD operations.

## 📋 Overview

This system digitizes core administrative tasks for hostel management, providing a single source of truth for student records, room status, and complaints. It's designed as a beginner-friendly Spring Boot application focusing on Create and Read operations.

## ✨ Features

### 1. 👨‍🎓 Student Admission Management
- Register new students with full details
- View all registered students
- Track room assignments for each student

### 2. 🏠 Room Inventory & Allocation
- Add new rooms to the hostel system
- View all rooms with their status (Vacant/Occupied)
- Allocate vacant rooms to students
- Automatic status updates upon allocation

### 3. 📝 Complaint Handling
- Students can submit complaints (maintenance, food, etc.)
- Admins can view all submitted complaints
- Simple, no-login complaint submission

### 4. 📊 Admin Dashboard
- Central navigation hub
- Quick access to all features
- Simple, intuitive interface

## 🛠️ Technology Stack

| Component | Technology |
|-----------|-----------|
| **Backend Framework** | Spring Boot 3.5.7 |
| **Web Framework** | Spring MVC |
| **Template Engine** | Thymeleaf |
| **Database** | H2 In-Memory Database |
| **Data Access** | Spring Data JPA |
| **Build Tool** | Maven |
| **Java Version** | Java 21 |

## 📁 Project Structure

```
hostel/
├── src/
│   ├── main/
│   │   ├── java/com/hostelmanagement/hostel/
│   │   │   ├── HostelApplication.java          # Main Spring Boot Application
│   │   │   ├── model/
│   │   │   │   ├── Student.java                # Student entity
│   │   │   │   ├── Room.java                   # Room entity
│   │   │   │   └── Complaint.java              # Complaint entity
│   │   │   ├── repository/
│   │   │   │   ├── StudentRepository.java      # Student data access
│   │   │   │   ├── RoomRepository.java         # Room data access
│   │   │   │   └── ComplaintRepository.java    # Complaint data access
│   │   │   └── controller/
│   │   │       ├── DashboardController.java    # Homepage
│   │   │       ├── StudentController.java      # Student operations
│   │   │       ├── RoomController.java         # Room operations
│   │   │       ├── AllocationController.java   # Room allocation
│   │   │       └── ComplaintController.java    # Complaint operations
│   │   └── resources/
│   │       ├── application.properties          # Configuration
│   │       └── templates/                      # Thymeleaf HTML views
│   │           ├── admin-dashboard.html
│   │           ├── register-student.html
│   │           ├── students-list.html
│   │           ├── add-room.html
│   │           ├── rooms-list.html
│   │           ├── allocate-room.html
│   │           ├── submit-complaint.html
│   │           └── complaints-list.html
│   └── test/
├── pom.xml                                     # Maven dependencies
├── IMPLEMENTATION_SUMMARY.md                   # Detailed documentation
├── QUICK_START_GUIDE.md                       # Quick start instructions
└── PRD.txt                                    # Product Requirements Document
```

## 🚀 Getting Started

### Prerequisites
- **Java 21** or higher
- **Maven** (Maven Wrapper included)
- **Web Browser**

### Running the Application

#### Option 1: Maven Wrapper (Recommended)
```bash
# Windows
cd D:\hostel\hostel
mvnw.cmd spring-boot:run

# Linux/Mac
cd /path/to/hostel
./mvnw spring-boot:run
```

#### Option 2: IntelliJ IDEA
1. Open the project
2. Navigate to `HostelApplication.java`
3. Click the green "Run" button

### Accessing the Application

Once started, open your browser:
- **Application**: http://localhost:8080/
- **H2 Console**: http://localhost:8080/h2-console

## 📖 Usage Guide

### Typical Workflow:

1. **Add Rooms**
   - Navigate to "Add New Room"
   - Create rooms (e.g., "101A", "102B", "103C")

2. **Register Students**
   - Go to "Register New Student"
   - Fill in student details

3. **Allocate Rooms**
   - Click "Allocate Room"
   - Select student and vacant room
   - Assign room (status updates automatically)

4. **Manage Complaints**
   - Students submit complaints via "Submit Complaint"
   - Admins view all complaints via "View Complaints"

## 🗺️ URL Routes

| Route | Method | Description |
|-------|--------|-------------|
| `/` or `/admin` | GET | Admin Dashboard |
| `/students/new` | GET | Show register form |
| `/students` | POST | Save new student |
| `/students` | GET | List all students |
| `/rooms/new` | GET | Show add room form |
| `/rooms` | POST | Save new room |
| `/rooms` | GET | List all rooms |
| `/allocate` | GET | Show allocation form |
| `/allocate` | POST | Assign room to student |
| `/complaint` | GET | Show complaint form |
| `/complaint` | POST | Save complaint |
| `/admin/complaints` | GET | List all complaints |
| `/h2-console` | GET | H2 Database Console |

## 💾 Database Schema

### Student Table
- `id` (PK, auto-increment)
- `full_name`
- `email`
- `phone_number`
- `guardian_name`
- `room_id` (FK → Room)

### Room Table
- `id` (PK, auto-increment)
- `room_number`
- `status` (Vacant/Occupied)

### Complaint Table
- `id` (PK, auto-increment)
- `student_name`
- `room_number`
- `complaint_details`

## 🔑 Key Design Decisions

### Entity Relationships
- **Student ↔ Room**: OneToOne relationship
- Student has a `Room` field annotated with `@OneToOne`
- Allows tracking of room assignments

### Repository Pattern
- Custom queries:
  - `findByRoomIsNull()` - Find unassigned students
  - `findByStatus(String)` - Find rooms by status

### MVC Architecture
- **Models**: JPA entities representing database tables
- **Views**: Thymeleaf HTML templates
- **Controllers**: Handle HTTP requests and business logic

## 🚫 Intentional Limitations (Per PRD)

This is a beginner-level project. The following are **NOT** implemented:
- ❌ User authentication/login
- ❌ User roles (ROLE_ADMIN, ROLE_STUDENT)
- ❌ Edit/Delete operations (only Create & Read)
- ❌ Complaint status updates
- ❌ Fee management
- ❌ Attendance tracking
- ❌ Advanced reporting

## 🧪 Testing

### Manual Testing Checklist:
- [ ] Add 3 rooms
- [ ] Register 2 students
- [ ] Allocate rooms to students
- [ ] Verify room status changes to "Occupied"
- [ ] Submit 2 complaints
- [ ] View all students (check room assignments)
- [ ] View all rooms (check status)
- [ ] View all complaints

## 📝 Configuration

### H2 Database (application.properties)
```properties
spring.datasource.url=jdbc:h2:mem:hosteldb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
```

**Note**: Data is stored in-memory and resets on application restart.

## 🤝 Contributing

This is a learning project built according to a specific PRD. Contributions should:
1. Follow the existing MVC pattern
2. Maintain beginner-friendly code
3. Not add out-of-scope features
4. Include Thymeleaf views for any new features

## 📚 Learning Resources

This project demonstrates:
- Spring Boot application structure
- MVC design pattern
- JPA entity relationships
- Spring Data repositories
- Thymeleaf templating
- RESTful URL design
- Form handling (GET/POST)

## 📄 License

This is an educational project.

## 👥 Target Audience

- **Primary**: Hostel administrators and wardens
- **Secondary**: Students (complaint submission)

## 🎯 Learning Outcomes

After studying this project, you will understand:
- How to structure a Spring Boot MVC application
- Entity relationship mapping with JPA
- Repository pattern with Spring Data
- Controller design and HTTP method handling
- Thymeleaf template integration
- H2 database configuration
- Maven project management

---

## 🆘 Support

For issues or questions:
1. Check `IMPLEMENTATION_SUMMARY.md` for detailed documentation
2. See `QUICK_START_GUIDE.md` for troubleshooting
3. Review `PRD.txt` for requirements clarification

---

**Built with ❤️ using Spring Boot**

**Status**: ✅ Complete and Ready to Run
**Version**: 1.0.0
**Last Updated**: November 2025

