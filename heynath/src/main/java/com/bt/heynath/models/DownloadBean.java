package com.bt.heynath.models;

/**
 * Created by bt18 on 03/18/2017.
 */

public class DownloadBean
{
    public long getDownloadId() {
        return DownloadId;
    }

    public void setDownloadId(long downloadId) {
        DownloadId = downloadId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSavedPath() {
        return SavedPath;
    }

    public void setSavedPath(String savedPath) {
        SavedPath = savedPath;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public  long DownloadId;
    public String Url,SavedPath,Status;
}
