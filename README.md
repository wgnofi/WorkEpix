# WorkEpix 🚀


**WorkEpix** is a slick mobile app that makes workplace life a breeze. Access company policies, request leaves, raise issues, and stay updated with manager announcements—all in one place. Powered by **Jetpack Compose** and **Firebase**, it’s fast, secure, and employee-friendly.

## What's Inside? ✨
- **Policy Quick Views**: AI-summarized health & overtime policies, searchable in seconds.
- **Leave Requests**: Apply and get approvals with real-time notifications.
- **Issue Tracker**: Flag concerns to managers, track status easily.
- **Announcements**: Stay in the loop with company updates.
- **Secure Login**: Firebase Auth with Google Sign-In & JWT for employees/managers.

## Tech Stack 🛠️
- **Frontend**: Jetpack Compose, AndroidX
- **Backend**: Firebase (Firestore, Auth)
- **DI**: Hilt
- **Language**: Kotlin

## Get Started ⚡
1. Clone this repo: `git clone https://github.com/your-repo/workepix.git`
2. Open in **Android Studio**.
3. Set up a **Firebase project** (Firestore + Auth).
4. Add `google-services.json` to `app/`.
5. Build & run on an Android device/emulator (API 21+).

## Note 📝

In [GenerativeModelModule.kt](https://github.com/wgnofi/WorkEpix/blob/main/app/src/main/java/com/example/standardprotocols/di/GenerativeModelModule.kt) Don't Forget to include your API KEY!

```
fun provideGenerativeModel(): GenerativeModel {
        val apiKey = "YOUR_API_KEY"
        return GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = apiKey
        )
    }
```

and Include your own firebase JSON configuration file in the root (app level) of android project !

## Screenshots 📸

![s1](https://github.com/user-attachments/assets/fc0820b2-24f8-4655-bd24-2470d26a697c)
![s5](https://github.com/user-attachments/assets/77411545-a0be-434b-a523-f4de2a3cef29)
![s4](https://github.com/user-attachments/assets/56a3647a-4ce5-4f1b-9a2b-c25d775f0ab4)
![s3](https://github.com/user-attachments/assets/853bca75-e052-4758-8b7d-d47d28d53d68)
![s2](https://github.com/user-attachments/assets/b0b7ebbc-5ad9-474b-8696-99a4ff5fa485)



## Dependencies 📦
- Firebase (Firestore, Auth, App Check)
- Jetpack Compose, AndroidX
- Hilt, GoogleID, Credential Manager
- JUnit (testing)

Check `build.gradle` for the full list.

## Contribute 🤝
Love to have you onboard! 
- Fork the repo.
- Create a branch (`feature/your-cool-idea`).
- Submit a PR with clear details.

## License 📜
MIT License.

