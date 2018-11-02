package com.ccee.videotool.databinding;
import com.ccee.videotool.R;
import com.ccee.videotool.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityVideoDetailBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linear_bottom, 10);
    }
    // views
    @NonNull
    public final android.widget.ImageView ivCover;
    @NonNull
    public final android.widget.LinearLayout linearBottom;
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.view.View mboundView7;
    @NonNull
    private final android.widget.TextView mboundView9;
    @NonNull
    public final android.widget.TextView tvActegory;
    @NonNull
    public final android.widget.TextView tvCenter;
    // variables
    @Nullable
    private com.ccee.videotool.model.entities.response.VideoDetailBean mData;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityVideoDetailBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.ivCover = (android.widget.ImageView) bindings[1];
        this.ivCover.setTag(null);
        this.linearBottom = (android.widget.LinearLayout) bindings[10];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.view.View) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView9 = (android.widget.TextView) bindings[9];
        this.mboundView9.setTag(null);
        this.tvActegory = (android.widget.TextView) bindings[5];
        this.tvActegory.setTag(null);
        this.tvCenter = (android.widget.TextView) bindings[8];
        this.tvCenter.setTag(null);
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
            setData((com.ccee.videotool.model.entities.response.VideoDetailBean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setData(@Nullable com.ccee.videotool.model.entities.response.VideoDetailBean Data) {
        this.mData = Data;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.data);
        super.requestRebind();
    }
    @Nullable
    public com.ccee.videotool.model.entities.response.VideoDetailBean getData() {
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
        int dataAuditStatusInt3ViewVISIBLEViewGONE = 0;
        java.lang.String dataCategoryTitle = null;
        boolean dataProductInfoJavaLangObjectNull = false;
        int dataVideoIdJavaLangObjectNullViewGONEViewVISIBLE = 0;
        java.lang.String dataTitle = null;
        java.lang.String dataAddTime = null;
        java.lang.Integer dataVideoId = null;
        com.ccee.videotool.model.entities.response.VideoDetailBean data = mData;
        int dataAuditStatusInt3TvCenterAndroidColorC3colorTvCenterAndroidColorC7color = 0;
        java.lang.String dataCoverImgUrl = null;
        com.ccee.videotool.model.entities.response.VideoDetailBean.ProductBean dataProductInfo = null;
        boolean dataAuditStatusInt3 = false;
        java.lang.String dataDescription = null;
        boolean dataVideoIdJavaLangObjectNull = false;
        int dataAuditStatus = 0;
        java.lang.String dataProductInfoTitle = null;
        int dataAuditStatusInt3TvCenterAndroidColorC6colorTvCenterAndroidColorC1color = 0;
        java.lang.String dataProductInfoJavaLangObjectNullDataProductInfoTitleMboundView6AndroidStringNoneArticle = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (data != null) {
                    // read data.categoryTitle
                    dataCategoryTitle = data.getCategoryTitle();
                    // read data.title
                    dataTitle = data.getTitle();
                    // read data.addTime
                    dataAddTime = data.getAddTime();
                    // read data.videoId
                    dataVideoId = data.getVideoId();
                    // read data.coverImgUrl
                    dataCoverImgUrl = data.getCoverImgUrl();
                    // read data.productInfo
                    dataProductInfo = data.getProductInfo();
                    // read data.description
                    dataDescription = data.getDescription();
                    // read data.auditStatus
                    dataAuditStatus = data.getAuditStatus();
                }


                // read data.videoId == null
                dataVideoIdJavaLangObjectNull = (dataVideoId) == (null);
                // read data.productInfo != null
                dataProductInfoJavaLangObjectNull = (dataProductInfo) != (null);
                // read data.auditStatus == 3
                dataAuditStatusInt3 = (dataAuditStatus) == (3);
            if((dirtyFlags & 0x3L) != 0) {
                if(dataVideoIdJavaLangObjectNull) {
                        dirtyFlags |= 0x20L;
                }
                else {
                        dirtyFlags |= 0x10L;
                }
            }
            if((dirtyFlags & 0x3L) != 0) {
                if(dataProductInfoJavaLangObjectNull) {
                        dirtyFlags |= 0x800L;
                }
                else {
                        dirtyFlags |= 0x400L;
                }
            }
            if((dirtyFlags & 0x3L) != 0) {
                if(dataAuditStatusInt3) {
                        dirtyFlags |= 0x8L;
                        dirtyFlags |= 0x80L;
                        dirtyFlags |= 0x200L;
                }
                else {
                        dirtyFlags |= 0x4L;
                        dirtyFlags |= 0x40L;
                        dirtyFlags |= 0x100L;
                }
            }


                // read data.videoId == null ? View.GONE : View.VISIBLE
                dataVideoIdJavaLangObjectNullViewGONEViewVISIBLE = ((dataVideoIdJavaLangObjectNull) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                // read data.auditStatus == 3 ? View.VISIBLE : View.GONE
                dataAuditStatusInt3ViewVISIBLEViewGONE = ((dataAuditStatusInt3) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                // read data.auditStatus == 3 ? @android:color/c3color : @android:color/c7color
                dataAuditStatusInt3TvCenterAndroidColorC3colorTvCenterAndroidColorC7color = ((dataAuditStatusInt3) ? (getColorFromResource(tvCenter, R.color.c3color)) : (getColorFromResource(tvCenter, R.color.c7color)));
                // read data.auditStatus == 3 ? @android:color/c6color : @android:color/c1color
                dataAuditStatusInt3TvCenterAndroidColorC6colorTvCenterAndroidColorC1color = ((dataAuditStatusInt3) ? (getColorFromResource(tvCenter, R.color.c6color)) : (getColorFromResource(tvCenter, R.color.c1color)));
        }
        // batch finished

        if ((dirtyFlags & 0x800L) != 0) {

                if (dataProductInfo != null) {
                    // read data.productInfo.title
                    dataProductInfoTitle = dataProductInfo.getTitle();
                }
        }

        if ((dirtyFlags & 0x3L) != 0) {

                // read data.productInfo != null ? data.productInfo.title : @android:string/none_article
                dataProductInfoJavaLangObjectNullDataProductInfoTitleMboundView6AndroidStringNoneArticle = ((dataProductInfoJavaLangObjectNull) ? (dataProductInfoTitle) : (mboundView6.getResources().getString(R.string.none_article)));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.ccee.videotool.utils.BindingAdapter.img(this.ivCover, dataCoverImgUrl);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, dataTitle);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, dataAddTime);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, dataDescription);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, dataProductInfoJavaLangObjectNullDataProductInfoTitleMboundView6AndroidStringNoneArticle);
            this.mboundView7.setVisibility(dataVideoIdJavaLangObjectNullViewGONEViewVISIBLE);
            this.mboundView9.setVisibility(dataAuditStatusInt3ViewVISIBLEViewGONE);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvActegory, dataCategoryTitle);
            this.tvCenter.setVisibility(dataVideoIdJavaLangObjectNullViewGONEViewVISIBLE);
            android.databinding.adapters.ViewBindingAdapter.setBackground(this.tvCenter, android.databinding.adapters.Converters.convertColorToDrawable(dataAuditStatusInt3TvCenterAndroidColorC6colorTvCenterAndroidColorC1color));
            this.tvCenter.setTextColor(dataAuditStatusInt3TvCenterAndroidColorC3colorTvCenterAndroidColorC7color);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    @NonNull
    public static ActivityVideoDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityVideoDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityVideoDetailBinding>inflate(inflater, com.ccee.videotool.R.layout.activity_video_detail, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityVideoDetailBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityVideoDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ccee.videotool.R.layout.activity_video_detail, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityVideoDetailBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityVideoDetailBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_video_detail_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityVideoDetailBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): data
        flag 1 (0x2L): null
        flag 2 (0x3L): data.auditStatus == 3 ? View.VISIBLE : View.GONE
        flag 3 (0x4L): data.auditStatus == 3 ? View.VISIBLE : View.GONE
        flag 4 (0x5L): data.videoId == null ? View.GONE : View.VISIBLE
        flag 5 (0x6L): data.videoId == null ? View.GONE : View.VISIBLE
        flag 6 (0x7L): data.auditStatus == 3 ? @android:color/c3color : @android:color/c7color
        flag 7 (0x8L): data.auditStatus == 3 ? @android:color/c3color : @android:color/c7color
        flag 8 (0x9L): data.auditStatus == 3 ? @android:color/c6color : @android:color/c1color
        flag 9 (0xaL): data.auditStatus == 3 ? @android:color/c6color : @android:color/c1color
        flag 10 (0xbL): data.productInfo != null ? data.productInfo.title : @android:string/none_article
        flag 11 (0xcL): data.productInfo != null ? data.productInfo.title : @android:string/none_article
    flag mapping end*/
    //end
}