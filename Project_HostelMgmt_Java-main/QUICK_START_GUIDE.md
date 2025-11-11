# 🚀 Quick Start Guide - Student Hostel Management System

## Prerequisites
- Java 21 (already configured in pom.xml)
- Maven (Maven Wrapper included in project)
- Web Browser

## Running the Application

### Windows Command Prompt
```cmd
cd D:\hostel\hostel
mvnw.cmd spring-boot:run
```

### Windows PowerShell
```powershell
cd D:\hostel\hostel
.\mvnw.cmd spring-boot:run
```

### Using IntelliJ IDEA
1. Open the project folder: `D:\hostel\hostel`
2. Wait for Maven dependencies to download
3. Open: `src/main/java/com/hostelmanagement/hostel/HostelApplication.java`
4. Click the green "Run" button next to the main method

## Application URLs

Once started (look for "Started HostelApplication" in console):

- **Homepage**: http://localhost:8080/
- **H2 Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:hosteldb`
  - Username: `sa`
  - Password: (leave empty)

## 📖 User Guide

### 1. Admin Dashboard (Homepage)
- Access: http://localhost:8080/
- Shows all available navigation links

### 2. Room Management
**Add a New Room:**
- Click "Add New Room"
- Enter room number (e.g., "101A", "102B", "201")
- Select status (Vacant/Occupied)
- Click "Add Room"

**View All Rooms:**
- Click "View All Rooms"
- See list of all rooms with their status

### 3. Student Management
**Register a Student:**
- Click "Register New Student"
- Fill in:
  - Full Name
  - Email
  - Phone Number
  - Guardian Name
- Click "Register Student"

**View All Students:**
- Click "View All Students"
- See list with room assignments

### 4. Room Allocation
**Allocate a Room to Student:**
- Click "Allocate Room"
- Select student from dropdown (only unassigned students)
- Select vacant room from dropdown
- Click "Assign Room"
- **Note**: Room status automatically updates to "Occupied"

### 5. Complaint System
**Submit a Complaint (Student View):**
- Click "Submit Complaint (Student Page)"
- Fill in:
  - Your Name
  - Room Number
  - Complaint Details
- Click "Submit Complaint"

**View All Complaints (Admin View):**
- Click "View Complaints"
- See all submitted complaints

## 🧪 Testing Workflow

### Complete Test Scenario:

1. **Add Rooms First**
   - Add room "101A" (Vacant)
   - Add room "102B" (Vacant)
   - Add room "103C" (Vacant)

2. **Register Students**
   - Register "John Doe" (john@example.com, 1234567890, "Jane Doe")
   - Register "Alice Smith" (alice@example.com, 0987654321, "Bob Smith")

3. **Allocate Rooms**
   - Assign "101A" to "John Doe"
   - Assign "102B" to "Alice Smith"

4. **Verify Allocation**
   - Go to "View All Students" - should show room assignments
   - Go to "View All Rooms" - should show "Occupied" status

5. **Submit Complaints**
   - Submit complaint as "John Doe" from "101A" about "Broken light"
   - Submit complaint as "Alice Smith" from "102B" about "No hot water"

6. **View Complaints**
   - Go to "View Complaints"
   - Should see both complaints listed

## ⚠️ Important Notes

1. **In-Memory Database**: Data is lost when application stops
2. **No Authentication**: All pages are public (as per PRD)
3. **No Edit/Delete**: Only Create and Read operations (as per PRD)
4. **Room Allocation**: 
   - Can only allocate vacant rooms
   - Can only allocate to students without rooms
5. **Port 8080**: Make sure port 8080 is not in use

## 🛑 Stopping the Application

- Press `Ctrl + C` in the terminal
- Or close the terminal window

## 🔧 Troubleshooting

### Application won't start
- Check if port 8080 is already in use
- Verify Java 21 is installed: `java -version`

### No data showing
- Remember: H2 is in-memory, data resets on restart
- Add data through the forms first

### Page not found
- Verify application started successfully
- Check console for "Started HostelApplication"
- Ensure you're using correct port: 8080

## 📂 Project Files Overview

```
D:\hostel\hostel\
├── IMPLEMENTATION_SUMMARY.md (detailed documentation)
├── QUICK_START_GUIDE.md (this file)
├── PRD.txt (Product Requirements Document)
├── pom.xml (Maven configuration)
└── src/
    └── main/
        ├── java/com/hostelmanagement/hostel/
        │   ├── HostelApplication.java (MAIN CLASS)
        │   ├── model/ (3 entities)
        │   ├── repository/ (3 repositories)
        │   └── controller/ (5 controllers)
        └── resources/
            ├── application.properties (DB config)
            └── templates/ (8 HTML views)
```

## ✅ Success Indicators

When everything is working correctly, you should see:

1. **In Console:**
   ```
   Started HostelApplication in X.XXX seconds (JVM running for Y.YYY)
   ```

2. **In Browser (http://localhost:8080/):**
   - Admin Dashboard with 7 navigation links
   - Clean, simple HTML interface
   - All links working

3. **Data Flow:**
   - Can add rooms → Can register students → Can allocate rooms → Can submit complaints → Can view all data

## 🎓 Learning Outcomes

This project demonstrates:
- Spring Boot application structure
- MVC (Model-View-Controller) pattern
- JPA/Hibernate entity relationships (OneToOne)
- Spring Data JPA repositories
- Thymeleaf templating
- H2 in-memory database
- RESTful URL mapping
- Form handling with GET/POST

---

**Ready to Start?**
```cmd
cd D:\hostel\hostel
mvnw.cmd spring-boot:run
```

Then open: **http://localhost:8080/**

🎉 **Enjoy your Hostel Management System!**

