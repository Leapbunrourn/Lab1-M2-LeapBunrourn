// Models.kt
// Data classes for .Student .Course Registration System

data class Student(
    val id: String,
    val name: String,
    val email: String?,   // nullable - optional email
    val major: String
)

data class Course(
    val courseId: String,
    val courseName: String,
    val credits: Int
)

data class Enrollment(
    val studentId: String,
    val courseId: String
)