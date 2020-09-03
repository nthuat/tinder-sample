TinderSample is a Tinder-like sample based on modern Android application tech stack and MVVM architecture. It uses [Randomuser.me API](https://randomuser.me/api/0.4/?randomapi) for generating random user data.

## Tech stack
    - MVVM Architecture
    - Kotlin based + Coroutines for asynchronous
    - Hilt for dependency injection
    - JetPack:
        + Data Binding
        + LiveData
        + Lifecycle
        + ViewModel
        + Room Database
    - Retrofit2 & OkHttp3 for networking
    - JUnit4, Mockito, Truth for unit testing

## Features
    - Swipe left or right to see random people with detailed information
    - Swipe right to save as favorite user
    - Support offline mode for favorite list
    - Error handling