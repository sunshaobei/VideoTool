package com.sunsh.baselibrary.utils.sp;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.sunsh.baselibrary.utils.AppContextUtil;
import com.sunsh.baselibrary.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;


public class SpUtil {

    private static SpUtil instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String FILE_APP = "cifnesw";

    private SpUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_APP,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static SpUtil getInstance() {
        if (instance == null) {
            synchronized (SpUtil.class) {
                if (instance == null) {
                    instance = new SpUtil(AppContextUtil.getContext());
                }
            }
        }
        return instance;
    }


    public static void clearInstance() {
        if (instance != null) instance = null;
    }

    public void clearSp() {
        editor.clear();
        editor.apply();
    }


    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getSharePreferences() {
        return sharedPreferences;
    }

    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     *
     * @param map
     */
    public void putMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            if (null != object) {
                if (object instanceof String) {
                    editor.putString(key, (String) object);
                } else if (object instanceof Integer) {
                    editor.putInt(key, (Integer) object);
                } else if (object instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) object);
                } else if (object instanceof Float) {
                    editor.putFloat(key, (Float) object);
                } else if (object instanceof Long) {
                    editor.putLong(key, (Long) object);
                } else {
                    editor.putString(key, object.toString());
                }
            }

        }
        editor.apply();
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键的值
     * @param defaultObject 默认值
     * @return
     */

    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }


    public String getString(String key, String s) {
        return sharedPreferences.getString(key, s);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, SpKey.DEFAULE_VALUE_STR);
    }

    public int getInt(String key, int i) {
        return sharedPreferences.getInt(key, i);
    }

    public long getLong(String key, long i) {
        return sharedPreferences.getLong(key, i);
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, SpKey.DEFAULE_VALUE_LO);
    }

    public float getFloat(String key, float i) {
        return sharedPreferences.getFloat(key, i);
    }

    public boolean getBoolean(String key, boolean b) {
        return sharedPreferences.getBoolean(key, b);
    }

    public void putString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void putLong(String key, long l) {
        editor.putLong(key, l).apply();
    }

    public void putBoolean(String key, boolean b) {
        editor.putBoolean(key, b).apply();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public boolean isLogin() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return false;
        }
        if (System.currentTimeMillis() > getExpiresAt() * 1000) {
            ToastUtils.showShortToastSafe("您的token已过期，请重新登录");
            return false;
        }
        return true;
    }

    public String getToken() {
        return getString(SpKey.TOKEN);
    }

    public void saveToken(String s) {
        putString(SpKey.TOKEN, s);
    }

    public void saveExpiresAt(long s) {
        putLong(SpKey.EXPIRES_AT, s);
    }

    public long getExpiresAt() {
        return getLong(SpKey.EXPIRES_AT);
    }

    public void saveSupplier_title(String s) {
        putString(SpKey.SUPPLIER_TITLE, s);
    }

    public String getSupplier_title() {
        return getString(SpKey.SUPPLIER_TITLE);
    }

    public void saveSupplier_logo(String s) {
        putString(SpKey.SUPPLIER_LOGO, s);
    }

    public String getSupplier_logo() {
        return getString(SpKey.SUPPLIER_LOGO);
    }

    public void saveSupplier_account(String s) {
        putString(SpKey.SUPPLIER_ACCOUTN, s);
    }

    public String getSupplier_account() {
        return getString(SpKey.SUPPLIER_ACCOUTN);
    }

    public void clearSp4LoginOut() {
        saveToken("");
        saveSupplier_logo("");
        saveExpiresAt(0);
        saveSupplier_title("");
        saveSupplier_account("");
    }
}
