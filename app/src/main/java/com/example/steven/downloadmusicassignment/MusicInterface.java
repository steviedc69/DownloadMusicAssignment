package com.example.steven.downloadmusicassignment;

/**
 * Created by jbuy519 on 08/10/2014.
 */
public interface MusicInterface {

    /**
     * Executes necessary steps before the download
     */
    public void beforeDownload();


    /**
     * Executes the necessary steps on the activity after the download
     */
    public void downloadFinished();

    /**
     * Updates the progress of the download
     * @param progress The progress precentage
     */
    public void setProgressStatus(int progress);


}
