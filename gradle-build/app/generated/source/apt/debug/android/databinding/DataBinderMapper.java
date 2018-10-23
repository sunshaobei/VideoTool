
package android.databinding;
import com.ccee.videotool.BR;
@javax.annotation.Generated("Android Data Binding")
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 18;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.ccee.videotool.R.layout.activity_login:
                    return com.ccee.videotool.databinding.ActivityLoginBinding.bind(view, bindingComponent);
                case com.ccee.videotool.R.layout.activity_spalash:
                    return com.ccee.videotool.databinding.ActivitySpalashBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -237232145: {
                if(tag.equals("layout/activity_login_0")) {
                    return com.ccee.videotool.R.layout.activity_login;
                }
                break;
            }
            case 673185844: {
                if(tag.equals("layout/activity_spalash_0")) {
                    return com.ccee.videotool.R.layout.activity_spalash;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"
            ,"data"};
    }
}