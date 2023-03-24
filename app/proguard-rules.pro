-verbose
-dontpreverify
-optimizationpasses 5
-dontskipnonpubliclibraryclasses

-dontwarn org.conscrypt.**

-repackageclasses com.sanmer.geomag

-keep @androidx.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}
