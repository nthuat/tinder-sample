# tinder-sample

![build](https://github.com/nthuat/tinder-sample/workflows/Android%20CI/badge.svg) [![codecov](https://codecov.io/gh/nthuat/tinder-sample/branch/master/graph/badge.svg)](https://codecov.io/gh/nthuat/tinder-sample)

A Tinder-like sample based on modern Android application tech stack and MVVM architecture. It uses [Randomuser.me API](https://randomuser.me/api/0.4/?randomapi) for generating random user data.

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
   
## Architechture
<p align="center">
<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" width=500/>
</p>
<p align="center">
Source: https://developer.android.com/jetpack/guide
</p>

## Features
   - Swipe left or right to see random people with detailed information
   - Swipe right to save as favorite user
   - Support offline mode for favorite list
   - Error handling
    
## Demo
<p align="center">
<img src="https://github.com/nthuat/tinder-sample/blob/master/screenshots/demo.gif" width=500/>
</p>
