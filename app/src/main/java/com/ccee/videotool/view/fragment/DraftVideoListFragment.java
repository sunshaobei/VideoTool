package com.ccee.videotool.view.fragment;

import com.ccee.videotool.event.AllvideoListener;
import com.ccee.videotool.greendao.GreenDaoManager;
import com.ccee.videotool.model.dbdao.DBVideoDao;
import com.sunsh.baselibrary.rxbus.RxBus;

public class DraftVideoListFragment extends VideoListFragment {


    @Override
    protected void initUi() {
        super.initUi();
        overScrollLayout.setLoadMoreEnable(false);
        overScrollLayout.setFooterView(null);
    }


    @Override
    public void onRefresh() {
        if (isFirstLoad) return;
        datas.clear();
        datas.addAll(GreenDaoManager.getInstance().getDaoSession().getDBVideoDao().queryBuilder().orderDesc(DBVideoDao.Properties.AddTime).build().list());
        count = datas.size();
        RxBus.getDefault().post(new AllvideoListener.AllVideo(count, getVideoType()));
        overScrollLayout.refreshComplete();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    protected Integer getVideoType() {
        return -1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
