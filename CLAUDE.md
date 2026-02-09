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

This is a standard single-module Android application using Jetpack Compose for UI.

**Current Structure:**
- `MainActivity`: Single entry point with Compose UI
- `ui/theme/`: Compose theming (Color, Theme, Type)

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

## VCS / Git

- For short stories, prefer rebase over merge. If branch has more commits, use merge instead of rebase to slightly minimize merge conflicts.
- In case there are merge conflicts, inform about them, rather than resolving them.
- When pushing a branch, do not forget to set the upstream, so the next time we can directly use "git push" in this branch instead of specifying the origin and the destination branch