# krevetka Linux Emulator

Android app prototype for a no-root Linux environment launcher.

## Current status

This repository currently contains the first Android UI prototype:
- Debian, Arch Linux, and Alpine Linux menu cards
- a separate detail screen for each distribution
- navigation back to the main menu
- a GitHub Actions workflow that builds a debug APK

**Important:** this first version does not yet run Linux. PRoot, root filesystem downloads, persistent per-distro storage, and graphical desktop sessions still need to be implemented. The “Install (demo)” button only changes temporary UI state.

## Build

Open the repository's **Actions** tab and run **Build Android APK**, or push a commit to `main`. Download the artifact named `krevetka-linux-emulator-debug-apk` from a successful workflow run.

## Technical stack

- Kotlin / Android Activity
- Android Gradle Plugin 8.7.3
- Kotlin Gradle Plugin 2.0.21
- JDK 17
