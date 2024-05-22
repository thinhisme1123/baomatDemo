# Giữ lại các thuộc tính cần thiết để đảm bảo tính toàn vẹn của mã và hỗ trợ debug
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile, LineNumberTable

# Tối ưu hóa toàn bộ mã nguồn
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# Mã hóa toàn bộ các lớp, phương thức và biến
-keep class !com.example.finalmobileproject.** { *; }
-keepclassmembers class !com.example.finalmobileproject.** { *; }
