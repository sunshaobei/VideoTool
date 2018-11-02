package com.ccee.videotool.view.fragment;

import com.ccee.videotool.greendao.GreenDaoManager;
import com.ccee.videotool.model.dbdao.DBVideoDao;

public class DraftVideoListFragment extends VideoListFragment {


    @Override
    protected void initUi() {
        super.initUi();
        overScrollLayout.setLoadMoreEnable(false);
        overScrollLayout.setFooterView(null);
    }


    @Override
    public void onRefresh() {
        datas.clear();
        datas.addAll(GreenDaoManager.getInstance().getDaoSession().getDBVideoDao().queryBuilder().orderDesc(DBVideoDao.Properties.AddTime).build().list());
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
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
