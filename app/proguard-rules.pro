# Để loại bỏ Log trong quá trình biên dịch
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Rút gọn tên lớp, phương thức và biến
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep class * extends java.lang.annotation.Annotation { *; }


# Bảo vệ tất cả các lớp trong gói com.example.finalmobileproject
-keep class com.example.finalmobileproject.** { *; }

# Không loại bỏ các class và phương thức sử dụng Reflection
-keep class com.example.reflection.** { *; }
-dontwarn com.example.reflection.**

# Quy tắc ProGuard cho Gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Quy tắc ProGuard cho Glide
-keep class com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**

# Quy tắc ProGuard cho Firebase Auth
-keep class com.google.firebase.auth.** { *; }
-dontwarn com.google.firebase.auth.**

# Quy tắc ProGuard cho Firebase Database
-keep class com.google.firebase.database.** { *; }
-dontwarn com.google.firebase.database.**

# Giữ lại các lớp liên quan đến view binding
-keep class **.databinding.* { *; }
-keep class **.viewbinding.* { *; }

# Quy tắc ProGuard cho AppCompat
-keep class androidx.appcompat.** { *; }
-dontwarn androidx.appcompat.**

# Quy tắc ProGuard cho Material Components
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# Quy tắc ProGuard cho ConstraintLayout
-keep class androidx.constraintlayout.** { *; }
-dontwarn androidx.constraintlayout.**

# Giữ lại các lớp liên quan đến test nếu cần thiết
-keep class androidx.test.** { *; }
-dontwarn androidx.test.**

