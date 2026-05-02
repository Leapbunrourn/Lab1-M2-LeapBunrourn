// Main.kt
// Main function and menu system for .Student .Course Registration System

fun main() {
    val students = mutableListOf<Student>()
    val courses = mutableListOf<Course>()
    val enrollments = mutableListOf<Enrollment>()

    var running = true

    while (running) {
        displayMenu()
        print("Your selection: ")
        val input = readLine()?.trim() ?: ""

        when (input) {
            "1" -> registerStudent(students)
            "2" -> createCourse(courses)
            "3" -> enrollStudent(students, courses, enrollments)
            "4" -> viewCourseStudents(courses, students, enrollments)
            "5" -> viewAllStudents(students)
            "6" -> viewAllCourses(courses, enrollments)
            "7", "exit" -> {
                println("\nGoodbye! Thank you for using the .Student .Course Registration System.")
                running = false
            }
            else -> println("\n[!] Invalid option. Please enter a number from 1 to 7.\n")
        }
    }
}

fun displayMenu() {
    println("""
=== .Student .Course Registration System ===
1. Register new student
2. Create new course
3. Enroll student in course
4. View each course students
5. View all students
6. View all courses
7. Exit
 
HOW TO USE:
  • Type a number (1-7) to select an option
  • Press Enter to confirm your choice
  • Type 7 or 'exit' to quit the application
""")
}

// ─── Part 3: .Student Management ───────────────────────────────────────────────

fun registerStudent(students: MutableList<Student>) {
    println("\n--- Register New .Student ---")

    print("Enter .Student ID (e.g., ST001): ")
    val id = readLine()?.trim() ?: ""

    if (id.isBlank()) {
        println("[!] .Student ID cannot be empty.\n")
        return
    }
    if (students.any { it.id == id }) {
        println("[!] A student with ID '$id' already exists.\n")
        return
    }

    print("Enter .Student Name: ")
    val name = readLine()?.trim() ?: ""
    if (name.isBlank()) {
        println("[!] Name cannot be empty.\n")
        return
    }

    print("Enter .Student Email (press Enter to skip): ")
    val emailInput = readLine()?.trim()
    val email: String? = if (emailInput.isNullOrBlank()) null else emailInput

    print("Enter .Student Major: ")
    val major = readLine()?.trim() ?: ""
    if (major.isBlank()) {
        println("[!] Major cannot be empty.\n")
        return
    }

    val student = Student(id = id, name = name, email = email, major = major)
    students.add(student)

    println("\n✓ .Student registered successfully!")
    println("  ID: ${student.id} | Name: ${student.name} | Major: ${student.major} | Email: ${student.email ?: "N/A"}\n")
}

fun viewAllStudents(students: List<Student>) {
    println("\n--- All Registered Students ---")
    if (students.isEmpty()) {
        println("No students registered yet.\n")
        return
    }
    students.forEach { student ->
        println(
            "ID: ${student.id} | Name: ${student.name} | Major: ${student.major} | Email: ${student.email ?: "N/A"}"
        )
    }
    println("Total: ${students.size} student(s)\n")
}

// ─── Part 4: .Course Management ────────────────────────────────────────────────

fun createCourse(courses: MutableList<Course>) {
    println("\n--- Create New .Course ---")

    print("Enter .Course ID (e.g., CS101): ")
    val courseId = readLine()?.trim() ?: ""

    if (courseId.isBlank()) {
        println("[!] .Course ID cannot be empty.\n")
        return
    }
    if (courses.any { it.courseId == courseId }) {
        println("[!] A course with ID '$courseId' already exists.\n")
        return
    }

    print("Enter .Course Name: ")
    val courseName = readLine()?.trim() ?: ""
    if (courseName.isBlank()) {
        println("[!] .Course name cannot be empty.\n")
        return
    }

    print("Enter Number of Credits: ")
    val creditsInput = readLine()?.trim()
    val credits = creditsInput?.toIntOrNull()
    if (credits == null || credits <= 0) {
        println("[!] Please enter a valid positive number for credits.\n")
        return
    }

    val course = Course(courseId = courseId, courseName = courseName, credits = credits)
    courses.add(course)

    println("\n✓ .Course created successfully!")
    println("  ID: ${course.courseId} | Name: ${course.courseName} | Credits: ${course.credits}\n")
}

fun viewAllCourses(courses: List<Course>, enrollments: List<Enrollment>) {
    println("\n--- All Courses ---")
    if (courses.isEmpty()) {
        println("No courses created yet.\n")
        return
    }
    courses.forEach { course ->
        val enrolled = enrollments.count { it.courseId == course.courseId }
        println(
            "ID: ${course.courseId} | Name: ${course.courseName} | Credits: ${course.credits} | Enrolled: $enrolled student(s)"
        )
    }
    println("Total: ${courses.size} course(s)\n")
}

// ─── Part 5: .Enrollment System ────────────────────────────────────────────────

fun enrollStudent(
    students: List<Student>,
    courses: List<Course>,
    enrollments: MutableList<Enrollment>
) {
    println("\n--- Enroll .Student in .Course ---")

    print("Enter .Student ID: ")
    val studentId = readLine()?.trim() ?: ""
    val student = students.find { it.id == studentId }
    if (student == null) {
        println("[!] No student found with ID '$studentId'.\n")
        return
    }

    print("Enter .Course ID: ")
    val courseId = readLine()?.trim() ?: ""
    val course = courses.find { it.courseId == courseId }
    if (course == null) {
        println("[!] No course found with ID '$courseId'.\n")
        return
    }

    if (enrollments.any { it.studentId == studentId && it.courseId == courseId }) {
        println("[!] ${student.name} is already enrolled in ${course.courseName}.\n")
        return
    }

    val enrollment = Enrollment(studentId = studentId, courseId = courseId)
    enrollments.add(enrollment)

    println("\n✓ .Enrollment successful!")
    println("  ${student.name} (${student.id}) has been enrolled in ${course.courseName} (${course.courseId})\n")
}

fun viewCourseStudents(
    courses: List<Course>,
    students: List<Student>,
    enrollments: List<Enrollment>
) {
    println("\n--- View Students in .Course ---")

    print("Enter .Course ID: ")
    val courseId = readLine()?.trim() ?: ""
    val course = courses.find { it.courseId == courseId }

    if (course == null) {
        println("[!] No course found with ID '$courseId'.\n")
        return
    }

    val courseEnrollments = enrollments.filter { it.courseId == courseId }

    println("\n.Course: ${course.courseName} (${course.courseId}) | Credits: ${course.credits}")
    println("-------------------------------------------")

    if (courseEnrollments.isEmpty()) {
        println("No students enrolled in this course yet.\n")
        return
    }

    courseEnrollments.forEach { enrollment ->
        val student = students.find { it.id == enrollment.studentId }
        if (student != null) {
            println(
                "ID: ${student.id} | Name: ${student.name} | Major: ${student.major} | Email: ${student.email ?: "N/A"}"
            )
        }
    }
    println("Total enrolled: ${courseEnrollments.size} student(s)\n")
}