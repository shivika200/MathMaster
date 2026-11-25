# MathMaster -- Interactive Mathematics Learning

## 1. Project Overview

**MathMaster** is an interactive Android application designed to help
primary-school students improve their mathematical knowledge and
problem-solving skills. The application provides mathematics activities,
quizzes, puzzles, and learning progress tracking through a simple and
user-friendly interface.

The application is developed using **Kotlin and Jetpack Compose** in
Android Studio. It uses Room Database for local data persistence,
Retrofit for retrieving questions from an external API, and ViewModels
and repositories to organise application logic.

## 2. Core Features and Their Implementation

### 2.1 Home Screen

The Home Screen provides the main entry point to the application.

**Implementation:** - Developed using Jetpack Compose and Material 3
components. - Displays the available mathematics learning activities. -
Provides navigation to the Activity, Settings, and Statistics screens. -
Displays learning progress information.

### 2.2 Interactive Mathematics Activities

MathMaster provides different activities to practise mathematical
concepts.

**Main activities include:** - **Multiplication Challenge:** Practising
multiplication through mathematical questions. - **Mathematical
Puzzles:** Solving number sequences and logical mathematics problems. -
**Mathematics Quiz:** Answering multiple-choice questions and receiving
feedback.

**Implementation:** - Questions are represented using Kotlin data
classes. - Activity interfaces are developed using Jetpack Compose. -
ViewModels manage question loading, selected answers, quiz states, and
feedback. - Students receive feedback after submitting an answer. -
Correct answers and explanations support the learning process.

### 2.3 Online Question Retrieval

The application supports retrieving mathematics questions from an
external question API.

**Implementation:** - Retrofit communicates with the Open Trivia
Database API. - Gson converts JSON responses into application data. -
API responses are processed and converted into question models. -
Repository classes manage communication between the API and local
database. - Local questions provide fallback content when online
retrieval is unavailable.

### 2.4 Local Data Storage

The application uses local database storage to retain learning questions
and activity records.

**Implementation:** - Room Database provides structured local data
persistence. - `QuestionEntity` stores mathematics questions, answers,
explanations, categories, and difficulty levels. - `ProgressEntity`
stores selected answers, correct answers, results, and timestamps. -
Data Access Objects (DAOs) provide database operations, including
inserting and retrieving records. - The repository manages access to
local database information.

### 2.5 Learning Statistics and Progress Tracking

The Statistics screen allows students to review their learning
performance.

**Implementation:** - Room queries retrieve stored learning activity
records. - `StatisticsViewModel` processes the retrieved information. -
The application calculates total attempts, correct answers, incorrect
answers, and accuracy. - Progress information is displayed through
Compose UI components.

### 2.6 Settings and Personalisation

The Settings screen allows students to customise aspects of their
learning experience.

**Implementation:** - `SettingsScreen` provides the settings
interface. - `SettingsViewModel` manages application settings. -
SharedPreferences stores selected preferences locally. - Available
settings include learning difficulty, sound preferences, reminder
preferences, and text size.

### 2.7 Notifications and Background Work

The application includes reminder-related settings to support learning
activities.

**Implementation:** - Android notification permission is declared using
`POST_NOTIFICATIONS`. - Notification permission is requested at runtime
on supported Android versions. - WorkManager is used for managing
background work. - Reminder preferences are managed through the settings
functionality.

## 3. Technologies and Tools

  Technology           Purpose
  -------------------- ----------------------------------------
  Kotlin               Main programming language
  Android Studio       Application development environment
  Jetpack Compose      Building the user interface
  Material 3           UI components and visual design
  Room Database        Local data storage
  Retrofit             API communication
  Gson                 JSON data conversion
  ViewModel            Managing UI-related application data
  Repository Pattern   Organising data access
  Navigation Compose   Navigating between application screens
  SharedPreferences    Storing user settings
  WorkManager          Managing background tasks
  JUnit                Unit testing
  Compose UI Testing   Testing application interfaces
  Git and GitHub       Version control and project management

## 4. Application Architecture

**Architecture explanation:** - **UI Layer:** Displays screens and
receives student interactions. - **ViewModel Layer:** Manages
screen-related data and application logic. - **Repository Layer:**
Coordinates access to remote and local data. - **Data Layer:** Handles
API communication and database operations. - **Dependency Management:**
`AppContainer` provides application dependencies.

## 5. Installation and Setup

### Prerequisites

-   Android Studio
-   JDK compatible with the configured Gradle version
-   Android SDK
-   Internet connection for online question retrieval
-   Android emulator or physical Android device

### Steps to Run the Application

1.  Clone or download the MathMaster project from the repository.
2.  Open Android Studio.
3.  Select **Open** and choose the MathMaster project folder.
4.  Allow Gradle to synchronise the project.
5.  Connect an Android device or start an emulator.
6.  Select the `app` run configuration.
7.  Click **Run** to install and launch the application.

## 6. Testing

The application can be tested using:

-   **Unit Testing:** Testing individual functions and application
    logic.
-   **Database Testing:** Checking question and progress data
    operations.
-   **UI Testing:** Verifying screen visibility, navigation, and user
    interactions.
-   **Manual Testing:** Running the application on an emulator or
    physical device.

Run Android instrumented tests using:

``` bash
gradlew.bat connectedDebugAndroidTest
```

Check the test results in Android Studio after execution.

## 7. Privacy and Ethical Considerations

MathMaster is designed with consideration for the needs of young
learners.

-   Learning progress and preferences are stored locally using Room and
    SharedPreferences.
-   The application does not require unnecessary location information.
-   Notifications require user permission on supported Android versions.
-   Learning activities are designed to support education without
    relying on manipulative engagement techniques.
-   Online question retrieval requires an internet connection. Question
    suitability should be reviewed before use with children.
-   The application should be further evaluated for accessibility,
    privacy, and child-safety requirements before public release.

## 8. Future Improvements

Possible future improvements include:

-   Adding more age-appropriate mathematics topics.
-   Introducing additional difficulty levels.
-   Providing more detailed learning progress charts.
-   Improving offline question management.
-   Improving accessibility and support for different learning needs.
-   Conducting additional usability and child-safety testing.

## 9. Conclusion

MathMaster combines interactive mathematics activities, online question
retrieval, local data storage, personalised settings, and learning
progress tracking in an Android application. By using Kotlin, Jetpack
Compose, Room, Retrofit, and ViewModels, the project demonstrates the
implementation of a structured Android application that supports an
engaging mathematics learning experience.

---
