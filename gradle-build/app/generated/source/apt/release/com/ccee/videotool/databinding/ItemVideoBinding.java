package com.ccee.videotool.databinding;
import com.ccee.videotool.R;
import com.ccee.videotool.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ItemVideoBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    public final android.widget.ImageView icon;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final android.widget.TextView mboundView4;
    // variables
    @Nullable
    private com.ccee.videotool.model.entities.response.VideoListBean.VideoBean mData;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemVideoBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.icon = (android.widget.ImageView) bindings[1];
        this.icon.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.data == variableId) {
            setData((com.ccee.videotool.model.entities.response.VideoListBean.VideoBean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setData(@Nullable com.ccee.videotool.model.entities.response.VideoListBean.VideoBean Data) {
        this.mData = Data;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.data);
        super.requestRebind();
    }
    @Nullable
    public com.ccee.videotool.model.entities.response.VideoListBean.VideoBean getData() {
        return mData;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String dataDuration = null;
        java.lang.String dataTitle = null;
        java.lang.String dataAddTimeTextJavaLangString = null;
        com.ccee.videotool.model.entities.response.VideoListBean.VideoBean data = mData;
        java.lang.String dataCoverImgUrl = null;
        java.lang.String dataAuditStatusText = null;
        java.lang.String dataAddTimeText = null;
        java.lang.String dataAddTimeTextJavaLangStringDataDuration = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (data != null) {
                    // read data.duration
                    dataDuration = data.getDuration();
                    // read data.title
                    dataTitle = data.getTitle();
                    // read data.coverImgUrl
                    dataCoverImgUrl = data.getCoverImgUrl();
                    // read data.auditStatusText
                    dataAuditStatusText = data.getAuditStatusText();
                    // read data.addTimeText
                    dataAddTimeText = data.getAddTimeText();
                }


                // read (data.addTimeText) + (" · 时长 ")
                dataAddTimeTextJavaLangString = (dataAddTimeText) + (" · 时长 ");


                // read ((data.addTimeText) + (" · 时长 ")) + (data.duration)
                dataAddTimeTextJavaLangStringDataDuration = (dataAddTimeTextJavaLangString) + (dataDuration);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.ccee.videotool.utils.BindingAdapter.imagUrl(this.icon, dataCoverImgUrl);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, dataTitle);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, dataAddTimeTextJavaLangStringDataDuration);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, dataAuditStatusText);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    @NonNull
    public static ItemVideoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ItemVideoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ItemVideoBinding>inflate(inflater, com.ccee.videotool.R.layout.item_video, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ItemVideoBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ItemVideoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ccee.videotool.R.layout.item_video, null, false), bindingComponent);
    }
    @NonNull
    public static ItemVideoBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ItemVideoBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/item_video_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ItemVideoBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): data
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}