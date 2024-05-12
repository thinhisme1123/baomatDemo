# Để loại bỏ Log trong quá trình biên dịch
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Đảm bảo không mã hóa các class và phương thức được sử dụng trong Annotation
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.example.annotation.* <methods>;
}

# Không mã hóa và loại bỏ các class được sử dụng trong AndroidManifest.xml
-keep class com.example.activities.MainActivity { *; }

# Không loại bỏ các class và phương thức sử dụng Reflection
-keep class com.example.reflection.** { *; }
-dontwarn com.example.reflection.**

# Không loại bỏ các class và phương thức trong package com.example.models
-keep class com.example.models.** { *; }

# Không loại bỏ các thư viện bên thứ ba
-keep class org.example.library.** { *; }
-dontwarn org.example.library.**
