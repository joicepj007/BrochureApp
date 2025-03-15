# BrochureApp
**Bonial Brochure App**
**Overview**
This application was developed as part of the Bonial Android Developer Code Challenge. It displays a collection of brochures from retailers, allowing users to browse through available offers in a responsive grid layout.

**Features**

* Brochure Grid Display: Shows brochures in a responsive grid layout
* Premium Content Highlighting: Premium brochures are displayed full-width
* Distance-Based Filtering: Option to filter brochures within 5km
* Responsive Layout: Adapts to both portrait (2 columns) and landscape orientations (3 columns)
* Placeholder Support: Shows placeholder images when brochure images are unavailable

**Technical Overview**
**Architecture**
The application follows **MVVM with Clean Architecture** principles, organized in a modular structure:

* **:domain**: Contains business logic, models, and use cases independent of any framework
* **:data**: Implements repositories and data sources that interact with the API
* **:presentation**: Houses UI components, ViewModels, and navigation
* **:designsystem**: Maintains reusable UI components and design tokens
* **:common:** Provides shared utilities, constants, and base components used across all modules

This architecture was chosen to:

* **Separate concerns:** Each layer has a distinct responsibility
* **Improve testability:** Business logic is isolated from Android framework dependencies
* **Enable scalability:** Additional features can be added without affecting existing components
* **Facilitate maintenance:** Changes in one layer don't ripple through the entire codebase

**Technologies & Libraries**

**UI**

* **Jetpack Compose:** Modern declarative UI toolkit for creating responsive interfaces
* **Material 3:** Provides a cohesive design system with ready-to-use components
* **Coil:** Efficient image loading with placeholder handling

**Dependency Injection**

* **Hilt:** Simplifies dependency injection setup and provides compile-time validation

**Asynchronous Programming**

* **Kotlin Coroutines:** Handles asynchronous operations with structured concurrency
* **Flow:** Provides reactive streams for UI state updates

**Networking**

* **Retrofit:** Type-safe HTTP client for API communication
* **Kotlinx Serialization:** Efficient JSON parsing

**Testing**

* **JUnit:** Basic unit testing framework
* **Mockito:** Creates mock dependencies for isolated testing
* **Turbine:** Simplifies Flow testing
* **MockWebServer:** Tests network interactions

**Design Patterns**
**Presentation Layer**

* **MVVM Pattern:** ViewModels expose UI state via StateFlow, while UI observes changes
* **State Hoisting:** UI state is lifted to higher-level components
* **Unidirectional Data Flow:** Data flows in a single direction from ViewModel to UI

**Domain Layer**

* **Repository Pattern:** Abstracts data access mechanisms
* **Use Case Pattern:** Encapsulates business logic in reusable components

**Design System**

* **Builder Pattern:** Used in component styling
* **Factory Pattern:** For theme component creation
* **Observer Pattern:** For theme changes
* **Composite Pattern:** For component hierarchies

**Performance Optimizations**

* **LazyVerticalGrid:** For efficient list rendering with minimal recomposition
* **Key-based Item Identification:** Prevents unnecessary recompositions during updates
* **Coil Image Loading:** Handles caching and memory management
* **StateFlow with Lifecycle Awareness:** Collects state only when the UI is visible

**Setup Instructions**

1. Clone the repository
2. Open the project in Android Studio.
3. Sync Gradle and run the app on an emulator or device

**API Information**
The app fetches data from the following endpoint:
Copyhttps://mobile-s3-test-assets.aws-sdlc-bonial.com/shelf.json

**Unit Tests**
* Run the tests using: ./gradlew test

**Future Improvements**

* Implement detail view for brochures
* Add search functionality
* Support offline caching
* Add animations for smoother transitions

License
This project is for demonstration purposes only.