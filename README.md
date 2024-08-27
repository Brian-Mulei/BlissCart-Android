## Android App Integration

The Android app for BlissCart is developed using Kotlin and follows modern Android development practices to provide a high-quality user experience. Here’s an overview of the key components and architecture:

### Architecture

- **MVVM (Model-View-ViewModel):**
  - **Role:** Organizes code into three layers—Model, View, and ViewModel—to separate concerns and improve maintainability.
  - **Responsibility:** The ViewModel handles business logic and prepares data for the View, while the Model represents the data and business rules.

### Key Components

- **Room:**
  - **Role:** Manages local database operations.
  - **Responsibility:** Provides a clean API for accessing the SQLite database, allowing for efficient storage and retrieval of data. Room integrates seamlessly with LiveData and ViewModel to provide a reactive data layer.

- **Retrofit:**
  - **Role:** Handles network operations.
  - **Responsibility:** Simplifies API calls and parsing of responses. Retrofit is used for fetching data from the backend, including product information, user details, and order management.

### Features

- **Product Browsing:** Users can explore products, view detailed information, and manage their cart.
- **Order Management:** Users can place orders, check their status, and view past orders.
- **Authentication:** Secure login and registration processes, integrated with JWT for backend communication.

For more details on the Android app implementation, 

For the backend side of blisscart please visit  the [BlissCart Spring boot repository](https://github.com/Brian-Mulei/blisscart).

