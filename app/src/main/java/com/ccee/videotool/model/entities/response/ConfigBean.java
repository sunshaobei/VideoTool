package com.ccee.videotool.model.entities.response;

import java.io.Serializable;
import java.util.List;

public class ConfigBean implements Serializable {


    /**
     * Description : ["鏂板瑙嗛褰曞埗銆佽鍓�佺紪杈戠瓑鍔熻兘","鏉�浜嗕竴涓▼搴忓憳绁ぉ"]
     * DownloadUrl : http://www.cifnews.com/app/cifnews.apk
     * Upgrade : false
     * Version : 10000
     * VersionName : 1.0
     * FileSize : 1.2M
     */

    private String DownloadUrl;
    private boolean Upgrade;
    private int Version;
    private String VersionName;
    private String FileSize;
    private List<String> Description;

    public String getDownloadUrl() {
        return DownloadUrl;
    }

    public void setDownloadUrl(String DownloadUrl) {
        this.DownloadUrl = DownloadUrl;
    }

    public boolean isUpgrade() {
        return Upgrade;
    }

    public void setUpgrade(boolean Upgrade) {
        this.Upgrade = Upgrade;
    }

    public int getVersion() {
        return Version;
    }

    public void setVersion(int Version) {
        this.Version = Version;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String VersionName) {
        this.VersionName = VersionName;
    }

    public String getFileSize() {
        return FileSize;
    }

    public void setFileSize(String FileSize) {
        this.FileSize = FileSize;
    }

    public List<String> getDescription() {
        return Description;
    }

    public void setDescription(List<String> Description) {
        this.Description = Description;
    }
}
