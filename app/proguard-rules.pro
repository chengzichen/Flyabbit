# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android-sdk-studio/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable



# Proguard

-basedirectory proguard-pro

-include proguard-normal.pro

-include proguard-self.pro


-include proguard-avi-loading.pro

#-include proguard-butterknife.pro

-include proguard-canary-debug.pro

-include proguard-canary-release.pro

-include proguard-constraint-layout.pro

-include proguard-design.pro

-include proguard-glide.pro

-include proguard-google-gson.pro

-include proguard-okhttp3-logging-interceptor.pro

-include proguard-okhttp3.pro

-include proguard-proguard-design.pro

-include proguard-recyclerview-v7.pro

-include proguard-retrofit-adapter-rxjava.pro

-include proguard-retrofit-converter-gson.pro

-include proguard-retrofit.pro

-include proguard-rxandroid.pro

-include proguard-rxjava.pro

-include proguard-rxlifecycle.pro

-include proguard-rxlifecycle-components.pro

-include proguard-rxlifecycle.pro

-include proguard-rxpermissions.pro

-include proguard-support-v4.pro

-include proguard-support-v7-appcompat.pro

-include proguard-banner.pro

-include proguard-rolambda.pro

-include proguard-flyco.pro

-include proguard-okio.pro

-include proguard-rxcache.pro

-include proguard-arouter.pro

-include proguard-databinding.pro
