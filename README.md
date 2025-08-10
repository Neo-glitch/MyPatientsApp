# MyPatientsApp

An Android application for managing patient records with **offline capabilities** and **cloud sync functionality**.  
Built with modern Android development tools and best practices.

---

## Features

- **Add Patient** – Create new patient records with name, age, gender, phone number, and medical condition.  
- **List Patients** – Display all saved patients with search functionality by name.  
- **Patient Details** – View complete patient information when selected.  
- **Edit & Delete** – Update or remove patient records.  
- **Offline Storage** – Data persists locally using Room Database.  
- **Manual Sync** – Upload unsynced records to a REST API using WorkManager.  
- **Modern UI** – Built entirely with Jetpack Compose following Material Design 3 guidelines.  
- **Theme Support** – Light and dark themes available.  
- **MVVM Architecture** – Clear separation between UI, business logic, and data layers.  
- **Dependency Injection** – Managed with Hilt.  
- **Async Operations** – Powered by Coroutines & Flow.  
- **Unit Testing** – Comprehensive tests for ViewModels and Repository classes.

---

## Technologies Used

- **Kotlin** – Primary development language  
- **Jetpack Compose** – Declarative UI framework  
- **Room Database** – Local storage  
- **Hilt** – Dependency injection  
- **Retrofit** – Network requests  
- **Coroutines & Flow** – Asynchronous programming  
- **Material Design 3** – Modern UI components and theming  
- **Navigation Component** – Screen navigation  
- **ViewModel & LiveData** – Architecture components  

---

## Project Structure
- **data/** –> Room entities, DAOs, repository implementations
- **domain/** –> Business models, usecases & repository interfaces
- **presentation/** –> Compose UI screens, UiStates, UiEvents & ViewModels
- **di/** –> Hilt dependency injection modules

---

## API Details

The app syncs with the [JSONPlaceholder API](https://my-json-server.typicode.com/Neo-glitch/MyPatientsServer/sync) for demonstration purposes.  
Clicking the **Sync** button uploads any unsynced local records to this mock endpoint.

---

## Project Setup

### 1. Clone the Project
- Git Clone the project

### 2. Open in Android Studio
- Launch Android Studio  
- Click **"Open an existing Android Studio project"**  
- Select the project directory  

### 3. Build the project
- Let Android Studio sync Gradle files  
- Build via **Build > Make Project**  

### 4. Run on device or emulator
- Connect an Android device or start an emulator  
- Click **Run** in Android Studio  

---

## Testing

Unit tests are available for:
- **ViewModels**
- **Repository**

---

## Issues and Limitations
- The sync feature uses JSONPlaceholder as a mock API, so data won't actually persist on the server
- Basic form validation is implemented but could be more comprehensive
- No real-time sync - manual sync only
- Search is currently limited to patient names only
