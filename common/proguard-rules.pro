# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.mujirenben.android.common.datapackage.http.**{*;}
-keep class com.mujirenben.android.common.service.**{*;}
-keep class com.mujirenben.android.common.datapackage.**{*;}
-keep class com.mujirenben.android.common.base.adapter.entity.**{*;}
-keep class com.mujirenben.android.common.base.greendao.**{*;}
-keep class com.mujirenben.android.common.entity.**{*;}
-keep class com.mujirenben.android.common.event.**{*;}
-keep class com.mujirenben.android.common.misc.**{*;}
-keep class com.mujirenben.android.common.pay.**{*;}
-keep class com.mujirenben.android.common.service.**{*;}
-keep class com.mujirenben.android.common.user.**{*;}
-keep class com.mujirenben.android.common.util.**{*;}
-keep class com.mujirenben.android.common.widget.**{*;}
-keep class com.mujirenben.android.common.arounter.**{*;}
-keep class com.mujirenben.android.common.base.interal.**{*;}
-keep class com.mujirenben.android.common.config.**{*;}
-keep class com.mujirenben.android.common.http.**{*;}
-keep class com.mujirenben.android.common.receiver.**{*;}

-keep class com.tencent.**{*;}