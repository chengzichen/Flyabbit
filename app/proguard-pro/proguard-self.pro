# ------------------------------- 自定义区 -------------------------------

#other moudle
-keep class com.fanyu.library.** { *; }
-keep interface com.fanyu.library.** { *; }
#-dontwarn com.fanyu.library.**
#-keep class Constants
#-keep  class com.fanyu.**modle.bean** { *; }
-keep public interface **.**Api*.** {*;}
#-keep  class com.fanyu.joys.home.app.constant** { *; }
-keep public class * extends android.support.v7.widget.RecyclerView.ViewHolder { *; }
-keep public class * extends com.alibaba.android.vlayout.DelegateAdapter.Adapter { *; }
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder { *; }

# ------------------------------- 自定义区 end -------------------------------
