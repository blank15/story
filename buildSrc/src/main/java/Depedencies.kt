object Modules {
    const val model = ":data:model"
    const val remote = ":data:remote"
    const val local = ":data:local"
    const val repository = ":data:repository"
    const val domain =":domain"
    const val homeFeature = ":features:home"
    const val authenticationFeature = ":features:authentication"
    const val navigation = ":navigation"
    const val ui = ":core:ui"
}

object Versions {
    const val kotlin = "1.7.0"
    const val coroutines = "1.5.9"
    const val material = "1.4.0"
    const val appCompat = "1.4.2"
    const val lifecycle = "2.5.1"
    const val fragment = "1.5.2"

    const val koin = "3.1.4"

    const val gson = "2.9.0"
    const val json = "20211205"
    const val okHttp = "4.9.3"
    const val retrofit = "2.9.0"
    const val nav = "2.3.5"
    const val core = "1.6.0"
    const val constraintLayout = "2.1.4"
    const val camera = "1.0.2"
    const val additionalCamera = "1.0.0-alpha28"
    const val shimmer = "0.5.0"

    const val glide = "4.12.0"
    const val imagePicker = "3.0.0-beta1"

    const val timber = "5.0.1"

    const val androidTestRunner = "1.4.0"
    const val espressoCore = "3.4.0"
    const val archCoreTest = "2.1.0"
    const val androidJunit = "1.1.3"
    const val junit = "4.13.2"
}

object Libraries {
    //  KOIN
    const val koin = "io.insert-koin:koin-android:${Versions.koin}"
    const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
    const val koinJava = "io.insert-koin:koin-android-compat:${Versions.koin}"

    // RETROFIT
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val squareupOkhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"

    // Image Loader
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideOkHttp = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"

    // ANIMATION
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"

    // LOGGER
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Image Picker
    const val imagePicker = "com.github.esafirm:android-image-picker:${Versions.imagePicker}"
}

object AndroidLibraries {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val core = "androidx.core:core:${Versions.core}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    // Fragment
    const val fragment = "androidx.fragment:fragment:${Versions.fragment}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    // Camera
    const val camera2 = "androidx.camera:camera-camera2:${Versions.camera}"
    const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.camera}"
    const val cameraView = "androidx.camera:camera-view:${Versions.additionalCamera}"
    const val cameraExtension = "androidx.camera:camera-extensions:${Versions.additionalCamera}"

    // KOTLIN
    const val kotlinCoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"


    // NAV
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
    const val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
}

object KotlinLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
}

object TestLibraries {
    // ANDROID TEST
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoCore}"
    const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    const val junitAndroid = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val junit = "junit:junit:${Versions.junit}"
    const val json = "org.json:json:${Versions.json}"
    const val fragmentNav = "androidx.fragment:fragment-testing:${Versions.fragment}"

    // KOIN
    const val koin = "io.insert-koin:koin-test:${Versions.koin}"

    // COROUTINE
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
}

object PluginLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav}"
}
