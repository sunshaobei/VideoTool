package com.ccee.videotool.databinding;
import com.ccee.videotool.R;
import com.ccee.videotool.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityVideoBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.describe, 9);
    }
    // views
    @NonNull
    public final android.widget.TextView describe;
    @NonNull
    public final com.sunsh.baselibrary.widgets.SearchEditText editDescribe;
    @NonNull
    public final com.sunsh.baselibrary.widgets.SearchEditText editTitle;
    @NonNull
    public final android.widget.ImageView ivCover;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.TextView mboundView7;
    @NonNull
    private final android.widget.TextView mboundView8;
    // variables
    @Nullable
    private com.ccee.videotool.model.data.VideoData mData;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityVideoBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 1);
        final Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.describe = (android.widget.TextView) bindings[9];
        this.editDescribe = (com.sunsh.baselibrary.widgets.SearchEditText) bindings[4];
        this.editDescribe.setTag(null);
        this.editTitle = (com.sunsh.baselibrary.widgets.SearchEditText) bindings[3];
        this.editTitle.setTag(null);
        this.ivCover = (android.widget.ImageView) bindings[1];
        this.ivCover.setTag(null);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.TextView) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView8 = (android.widget.TextView) bindings[8];
        this.mboundView8.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x40L;
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
            setData((com.ccee.videotool.model.data.VideoData) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setData(@Nullable com.ccee.videotool.model.data.VideoData Data) {
        updateRegistration(0, Data);
        this.mData = Data;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.data);
        super.requestRebind();
    }
    @Nullable
    public com.ccee.videotool.model.data.VideoData getData() {
        return mData;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeData((com.ccee.videotool.model.data.VideoData) object, fieldId);
        }
        return false;
    }
    private boolean onChangeData(com.ccee.videotool.model.data.VideoData Data, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.cover) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        else if (fieldId == BR.category) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.title) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.des) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.article) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
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
        boolean dataShow = false;
        java.lang.String dataCategory = null;
        java.lang.String dataTitle = null;
        java.lang.String dataBottomLeft = null;
        int dataShowViewVISIBLEViewGONE = 0;
        com.ccee.videotool.model.data.VideoData data = mData;
        java.lang.String dataArticle = null;
        java.lang.String dataBottomRight = null;
        java.lang.String dataDes = null;
        java.lang.String dataCover = null;

        if ((dirtyFlags & 0x7fL) != 0) {


            if ((dirtyFlags & 0x41L) != 0) {

                    if (data != null) {
                        // read data.show
                        dataShow = data.isShow();
                        // read data.bottomLeft
                        dataBottomLeft = data.getBottomLeft();
                        // read data.bottomRight
                        dataBottomRight = data.getBottomRight();
                    }
                if((dirtyFlags & 0x41L) != 0) {
                    if(dataShow) {
                            dirtyFlags |= 0x100L;
                    }
                    else {
                            dirtyFlags |= 0x80L;
                    }
                }


                    // read data.show ? View.VISIBLE : View.GONE
                    dataShowViewVISIBLEViewGONE = ((dataShow) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x45L) != 0) {

                    if (data != null) {
                        // read data.category
                        dataCategory = data.getCategory();
                    }
            }
            if ((dirtyFlags & 0x49L) != 0) {

                    if (data != null) {
                        // read data.title
                        dataTitle = data.getTitle();
                    }
            }
            if ((dirtyFlags & 0x61L) != 0) {

                    if (data != null) {
                        // read data.article
                        dataArticle = data.getArticle();
                    }
            }
            if ((dirtyFlags & 0x51L) != 0) {

                    if (data != null) {
                        // read data.des
                        dataDes = data.getDes();
                    }
            }
            if ((dirtyFlags & 0x43L) != 0) {

                    if (data != null) {
                        // read data.cover
                        dataCover = data.getCover();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x51L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.editDescribe, dataDes);
        }
        if ((dirtyFlags & 0x49L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.editTitle, dataTitle);
        }
        if ((dirtyFlags & 0x43L) != 0) {
            // api target 1

            com.ccee.videotool.utils.BindingAdapter.imagUrl(this.ivCover, dataCover);
        }
        if ((dirtyFlags & 0x45L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, dataCategory);
        }
        if ((dirtyFlags & 0x61L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, dataArticle);
        }
        if ((dirtyFlags & 0x41L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, dataBottomLeft);
            this.mboundView7.setVisibility(dataShowViewVISIBLEViewGONE);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView8, dataBottomRight);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    @NonNull
    public static ActivityVideoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityVideoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityVideoBinding>inflate(inflater, com.ccee.videotool.R.layout.activity_video, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityVideoBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityVideoBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ccee.videotool.R.layout.activity_video, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityVideoBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityVideoBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_video_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityVideoBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): data
        flag 1 (0x2L): data.cover
        flag 2 (0x3L): data.category
        flag 3 (0x4L): data.title
        flag 4 (0x5L): data.des
        flag 5 (0x6L): data.article
        flag 6 (0x7L): null
        flag 7 (0x8L): data.show ? View.VISIBLE : View.GONE
        flag 8 (0x9L): data.show ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}