# Daily Round Quiz

A daily quiz app for Android built with **Kotlin**, **Jetpack Compose** (Material 3), and **Clean Architecture + MVVM** — the Android assignment implementation.

## Features

- **Splash screen** — flame logo with a 1-second delay before the quiz loads.
- **10 questions fetched from the provided endpoint** (gist URL) via **Retrofit + Gson** through the **Repository Pattern** — nothing hardcoded; `correctOptionIndex` selects the right option.
- **Multiple choice** — 4 options per question; green highlight + bounce for the correct answer, red highlight + shake for a wrong selection.
- **2-second auto-advance** — the next question appears automatically after the answer is revealed.
- **Skip button** — moves to the next question without answering.
- **Swipe gestures** — swipe right for the next question, swipe left for the previous one.
- **Progress bar** — "Question X of Y" with an animated progress indicator.
- **Streak & badges** — milestone streaks at **3, 5, 7 and 10** award a streak level and show a "X questions streak achieved!!" banner ("Perfect streak achieved!!" at 10); the flame indicator lights up as the streak grows.
- **Result screen** — "Congratulations!" with correct answers, total questions, highest streak and skipped count, plus a **Restart** button.
- **Dark theme** — assignment palette: background `#101215`, card surface `#262B33`, correct `#31C45D`, wrong `#B61D1D`.

## Architecture

```
com.quizapp/
├── data/                      # Data layer
│   ├── remote/QuizApi.kt               # Retrofit endpoint (provided gist URL)
│   ├── datasource/QuizDataSource.kt    # Fetches questions from the network
│   ├── model/QuestionDto.kt            # DTO + toDomain() mapping
│   └── repository/QuizRepositoryImpl.kt  # Repository implementation
├── di/AppModule.kt            # Koin module (data layer + use cases + ViewModel)
├── domain/                    # Domain layer
│   ├── model/Question.kt, StreakResult.kt
│   ├── repository/QuizRepository.kt      # Repository contract
│   └── usecase/                          # GetQuestions, CalculateStreak,
│                                         # SubmitAnswer
├── presentation/              # Presentation layer (MVVM)
│   ├── navigation/QuizNavHost.kt         # Splash → Quiz → Result (typed nav args)
│   ├── quiz/QuizViewModel.kt             # StateFlow + coroutines (auto-advance Job)
│   ├── quiz/QuizScreen.kt                # Question UI, gestures, animations
│   ├── components/                       # QuestionCard, OptionButton,
│   │                                     # ProgressSection, StreakIndicator
│   ├── splash/SplashScreen.kt
│   └── result/ResultScreen.kt
└── MainActivity.kt, QuizApplication.kt   # Koin startKoin(), single activity
```

## Tech Stack

| Concern | Choice |
|---|---|
| Language | Kotlin 2.2.10 |
| UI | Jetpack Compose (BOM 2026.02.01), Material 3 |
| Architecture | Clean Architecture + MVVM, Repository Pattern, Use Cases |
| DI | Koin 4.1.1 (`koin-android`, `koin-androidx-compose`) |
| Async | Coroutines + StateFlow, `collectAsStateWithLifecycle()` |
| Navigation | Navigation Compose 2.9.8 (slide/fade transitions) |
| Serialization | Gson 2.14.0 |
| Networking | Retrofit 2.11.0 (`retrofit`, `converter-gson`) |
| Min SDK / Target | 24 / 36 (AGP 9.3.1, Gradle 9.5) |

## Build & Test

```bash
# Debug APK
gradlew.bat :app:assembleDebug

# Unit tests (streak / answer logic)
gradlew.bat :app:testDebugUnitTest
```

The debug APK is produced at `app/build/outputs/apk/debug/app-debug.apk`.

## Screenshots

*Splash → Quiz → Result* (add screenshots here).
