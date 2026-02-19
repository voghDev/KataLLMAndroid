# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

KataLLMAndroid is an Android application that displays a list of Large Language Models (LLMs). The project is developed using Claude Code and managed through GitHub.

**Package:** `es.voghdev.katallmandroid`

## Build Commands

```bash
# Build the project
./gradlew build

# Clean build
./gradlew clean build

# Assemble debug APK
./gradlew assembleDebug

# Assemble release APK
./gradlew assembleRelease

# Run lint checks
./gradlew lint

# Run unit tests
./gradlew test

# Run unit tests for debug build
./gradlew testDebugUnitTest

# Run instrumented tests (requires emulator/device)
./gradlew connectedAndroidTest

# Run specific test
./gradlew test --tests "es.voghdev.katallmandroid.ExampleUnitTest"
```

## Technology Stack

- **Language:** Kotlin 2.0.21
- **UI Framework:** Jetpack Compose with Material3
- **Build System:** Gradle with Kotlin DSL and Version Catalog (libs.versions.toml)
- **Min SDK:** 33 (Android 13)
- **Target/Compile SDK:** 35 (Android 14)
- **Java Version:** 11

## Architecture

This App will have the following architecture and best practices for Android development 

- MVVM / MVI for propagating ViewModel state to a UI written in Jetpack Compose
- kotlinx.coroutines and Flow for handling asynchronous and concurrent implementations
- Hilt for Dependency Injection
- Material Design (Material3) as the design system. Standard Android UI components will be priority, rather than custom ones
- retrofit and okhttp for network requests
- gson for parsing JSON objects
- For the moment, we will use a single module called "app". This may change in the future and we may want to modularize the App by layer and by feature.
- A simple architecture without unnecessary extra layers: UI (Activity/Fragment/Screen) - ViewModel - [UseCase] - [Repository] - Data Source
- The repository Layer will be optional. There will be no interfaces with one single implementation. If there is only one implementation, we will use a class.
- All UseCases, Repositories and Data Sources will return Flow. This way, we will be able to perform collection operations with them.
- The ViewModel layer will use kotlinx.coroutines or extension functions that wrap them to handle concurrency.
- UseCases can also be omitted if they only contain a single call to the Repository/DataSource. ViewModel can directly call the Repository/DataSource and we save layers in that case.
- Every screen will have a @Composable class called *Screen and a ViewModel called *ViewModel. For example ProfileScreen, ProfileViewModel. 
- Every ViewModel class will have a Unit Test class associated that will at least contain tests to assert collaborations between the ViewModel and external collaborators (UC, Repositories, DS)

The project uses:
- Version Catalog (`gradle/libs.versions.toml`) for centralized dependency management
- Kotlin Compose plugin for optimized Compose compilation

## Dependency Management

All dependencies are managed via the version catalog in `gradle/libs.versions.toml`. When updating dependencies:

1. Update versions in the `[versions]` section
2. Dependencies use SDK 35 - if upgrading to SDK 36+, verify AGP version supports it
3. Current AGP 8.9.1 is compatible with SDK 35 dependencies

**Known Compatibility:**
- androidx.core:core-ktx 1.17.0+ requires SDK 36 and AGP 8.9.1+
- Current stable configuration uses core-ktx 1.15.0 with SDK 35

## Code Style

- All text files must end with a newline character
- Package structure: `es.voghdev.katallmandroid`
- Do not add unnecessary comments in code or configuration files unless they are completely essential, such as documenting known bugs or critical issues in code or libraries

## VCS / Git

- For short stories, prefer rebase over merge. If branch has more commits, use merge instead of rebase to slightly minimize merge conflicts.
- In case there are merge conflicts, inform about them, rather than resolving them.
- When pushing a branch, do not forget to set the upstream, so the next time we can directly use "git push" in this branch instead of specifying the origin and the destination branch

## Pull Requests

- For every PR that is opened, check for available Android devices by running `adb devices`. If a connected device is found, install the debug APK on it using `./gradlew installDebug` to verify the build can be installed successfully on a real device or emulator.