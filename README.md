# CamOpenCV — Real-Time Camera Processing using OpenCV (Android + C++ + JNI)

CamOpenCV is a hybrid Android project demonstrating:

- Live **camera preview**
- Frame processing using **OpenCV (C++ native layer)**
- JNI integration between **Kotlin ↔ C++**
- Exporting processed output to a **Web Viewer**
- Clean Android architecture with reproducible build

This project is designed for R&D workflows, native image processing, and demonstrating Android ↔ C++ integration.

---

## 📌 Features

### 🟩 1. Real-time Camera Preview (Android / Kotlin)
- Uses CameraX to stream frames.
- Converts each frame into a ByteArray for native processing.

### 🟩 2. Native Image Processing (C++ / OpenCV)
- OpenCV runs inside JNI (arm64 + armeabi-v7a supported).
- Example transformation: **grayscale conversion**.

### 🟩 3. JNI Bridge
Kotlin → JNI → C++ → Kotlin

- Zero-copy frame passing  
- High speed native pipeline  

### 🟩 4. Web Viewer
Displays base64 processed frames for debugging and research.

---

## 📁 Project Structure

```
camopencv/
│
├── android-app/
│   └── app/
│       ├── build.gradle
│       ├── src/main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/example/camopencv/
│       │   │     ├── MainActivity.kt
│       │   │     └── NativeBridge.kt
│       │   ├── cpp/
│       │   │     ├── CMakeLists.txt
│       │   │     └── native-lib.cpp
│       │   └── jniLibs/
│       │         ├── arm64-v8a/libopencv_java4.so
│       │         └── armeabi-v7a/libopencv_java4.so
│
├── web-viewer/
│   ├── index.html
│   ├── viewer.js
│   └── sample_frame_base64.txt
│
└── README.md
```

---

## ⚙️ Tech Stack

| Layer | Technology |
|-------|------------|
| Android | Kotlin, CameraX |
| Native | C++, OpenCV |
| Bridge | JNI |
| Build | Gradle + CMake |
| Viewer | HTML + JavaScript |

---

## 🚀 How It Works (Architecture)

### 1️⃣ CameraX captures frames  
### 2️⃣ Kotlin passes frames to JNI  
### 3️⃣ C++ processes frames with OpenCV  
### 4️⃣ Processed frame returns to Kotlin  
### 5️⃣ Optional export to Web Viewer  

---

## 🛠️ Build Instructions

### Requirements:

- Android Studio  
- NDK  
- OpenCV JNI libs (included)  

### Steps:

1. Open Android Studio → Open `android-app`
2. Sync Gradle
3. Run on a device  

---

## 🌐 Web Viewer

Open `web-viewer/index.html` in any browser.  
It loads `sample_frame_base64.txt` and displays the image.

---

## 🧹 Clean & Build

```
./gradlew clean
./gradlew assembleDebug
```

Native rebuild: Android Studio → Build → Rebuild.

---

## 📄 License

MIT — free to use and modify.
