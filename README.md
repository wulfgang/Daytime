# Time-Based Icon Android App

A simple Android app built with Jetpack Compose that changes its app icon based on the time of day.

## Features

- **Dynamic App Icon**: The app icon automatically changes based on the current time:
  - **Morning (5:00 - 11:59)**: Rising Sun icon (orange)
  - **Noon (12:00 - 16:59)**: Full Sun icon (yellow)
  - **Afternoon (17:00 - 20:59)**: Setting Sun icon (dark orange)
  - **Night (21:00 - 4:59)**: Moon icon (purple)

- **Real-time Display**: Shows the current time and corresponding time period
- **Jetpack Compose UI**: Modern, declarative UI with Material 3 design
- **Automatic Updates**: Icon updates when the app resumes

## Technical Implementation

The app uses Android's `PackageManager` to enable/disable activity-aliases with different icons:

1. **Activity Aliases**: Four activity-aliases are defined in `AndroidManifest.xml`, each with a different icon
2. **Time Detection**: The app checks the current hour using `Calendar.getInstance()`
3. **Icon Switching**: Uses `PackageManager.setComponentEnabledSetting()` to enable the appropriate alias and disable others
4. **Real-time Updates**: The icon updates in `onCreate()` and `onResume()` lifecycle methods

## Requirements

- Android Studio Hedgehog | 2023.1.1 or later
- Android SDK API 24+ (Android 7.0)
- Kotlin 1.9.10+

## Building the App

1. Open the project in Android Studio
2. Sync the Gradle files
3. Run the app on an emulator or physical device

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/timebasediconapp/
│   │   ├── MainActivity.kt
│   │   └── ui/theme/
│   │       ├── Theme.kt
│   │       └── Type.kt
│   ├── res/
│   │   ├── drawable/          # Icon vectors
│   │   ├── values/            # Strings, colors, themes
│   │   └── xml/               # Backup rules
│   └── AndroidManifest.xml
├── build.gradle
└── proguard-rules.pro
```

## Customization

To modify the time periods or icons:

1. **Time Periods**: Edit the `when` statement in `setAppIconBasedOnTime()` method in `MainActivity.kt`
2. **Icons**: Replace the vector drawables in `res/drawable/` or create new mipmap icons
3. **Colors**: Modify the tint colors in the vector drawables or create new icon designs

## Notes

- The icon changes take effect immediately but may require a launcher refresh to be visible
- The app uses vector drawables for icons, but you can replace them with PNG/mipmap resources for higher quality
- The time detection uses the device's local time settings
