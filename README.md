# 📊 Monitoring App – Android (XML + MVVM)

An Android application for monitoring data indicators from an external API. Built with **Kotlin**, **MVVM**, **Clean Architecture**, and **Retrofit**, the app features real-time data fetching, modularization, and best practices for modern Android development.

---

## 🚀 Key Features

- Display a dashboard with a list of monitored indicators.
- Individual indicator detail screens with live data.
- Loading indicators and error handling.
- Structured with clean architecture and layer separation.
- Dependency injection via **Hilt**.
- Networking handled by **Retrofit** & **OkHttp**.
- Asynchronous operations using **Kotlin Coroutines**.
- UI implemented using **XML layouts** with ViewBinding.

---

## 🧩 Tech Stack

- **Language:** Kotlin  
- **UI:** XML layouts + ViewBinding  
- **Architecture:** Clean Architecture + MVVM  
- **DI:** Hilt  
- **Networking:** Retrofit, OkHttp  
- **Async:** Coroutines  
- **Layers/layout:** data, domain, presentation

---

## 📁 Project Structure

```
monitoring_app/
├── app/             # Application module
├── data/            # DTOs, API services, repository implementations
├── domain/          # Core models and repository interfaces
├── presentation/    # Activities, ViewModels, UI logic
└── di/              # Hilt dependency injection setup
```

---

## 🛠️ Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Daztery/monitoring_app.git
   ```
2. Open the project in **Android Studio (Electric Eel or newer)**.
3. Add your API base URL or key in `local.properties` or use `BuildConfig`.
4. Sync Gradle and run the application on an emulator or real device.

---

## 💡 Purpose

This application demonstrates:

- Standard Android UI development using XML and ViewBinding.
- Clean architecture patterns with separation into layers.
- Live API integration and reactive UI updates.
- Handling loading and error states effectively.
- Utilization of coroutines and dependency injection.

---

## 🧭 Future Enhancements

- Replace XML with Jetpack Compose or migrate to Data Binding.
- Add local caching for offline usage.
- Implement search/filtering for indicators.
- Create UI and unit tests.
- Enhance with animations and accessible UI options.
