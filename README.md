# Daily Round Quiz

A daily quiz Android app built with **Kotlin** and **Jetpack Compose** (Material 3). It fetches 10 questions from a remote endpoint via Retrofit, shows one multiple-choice question at a time, and tracks a streak as you answer — ending with a results screen and the option to restart.

The app follows **Clean Architecture + MVVM**, separating the code into `data`, `domain`, and `presentation` layers, with the **Repository Pattern** and **Use Cases** keeping the UI independent of data sources. Dependency injection is handled with **Koin**.

## Project Structure

```
app/src/main/java/com/quizapp/
├── data/                          # Data layer — networking, DTOs, repository impl
│   ├── mapper/QuestionMapper.kt           # DTO → domain mapping
│   ├── model/QuestionDto.kt               # Network DTO
│   ├── repository/QuizRepositoryImpl.kt   # Repository implementation
│   └── source/remote/RetrofitService.kt   # Retrofit API service
├── di/AppModule.kt               # Koin module (DI wiring)
├── domain/                       # Domain layer — business logic, no Android deps
│   ├── model/Question.kt, StreakResult.kt
│   ├── repository/QuizRepository.kt       # Repository contract
│   └── usecase/                          # GetQuestionsUseCase, CalculateStreakUseCase,
│                                         # SubmitAnswerUseCase
├── presentation/                 # Presentation layer (MVVM)
│   ├── navigation/NavGraph.kt, Screen.kt  # App navigation
│   ├── splash/SplashScreen.kt, SplashViewModel.kt
│   ├── quiz/QuizScreen.kt, QuizViewModel.kt, QuizUiState.kt
│   ├── result/ResultScreen.kt
│   └── components/                       # QuestionCard, OptionButton,
│                                         # ProgressSection, StreakIndicator
├── ui/theme/                     # Color, Theme, Type
├── MainActivity.kt
└── QuizApplication.kt           # Koin startKoin(), application entry point
```

## Features

- Splash screen that loads the quiz after a short delay
- 10 questions fetched from a remote endpoint (Retrofit + Gson) — nothing hardcoded
- Multiple-choice with visual feedback: green for correct, red for wrong
- Auto-advance to the next question, plus a skip button
- Swipe gestures to move between questions
- Streak tracking with milestone badges at 3, 5, 7 and 10
- Results screen with score, highest streak and skipped count, plus restart
- Dark theme with a custom color palette
