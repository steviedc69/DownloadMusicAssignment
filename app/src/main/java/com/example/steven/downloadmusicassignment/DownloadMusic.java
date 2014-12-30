package com.example.steven.downloadmusicassignment;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jbuy519 on 08/10/2014.
 */
public class DownloadMusic extends AsyncTask<String, String, String> {

    private static String TAG = "DownloadMusic";

    private MusicInterface mI;
    private URL url;
    public DownloadMusic(MusicInterface mI){
        this.mI = mI;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mI.downloadFinished();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        int value = Integer.parseInt(values[0]);
        mI.setProgressStatus(value);
    }

    @Override
    protected String doInBackground(String... params) {

        int count;
        HttpURLConnection connection = null;
        OutputStream output = null;
        InputStream input = null;


        try{
			// TODO: add code to download music file
			// add it to local repo
            url = new URL(params[0]);
            connection =(HttpURLConnection)url.openConnection();
            int lengthOfFile = connection.getContentLength();
            input = new BufferedInputStream(url.openStream(),10*1024);
            String path = Environment.getExternalStorageDirectory().getPath()+"/test.mp3";
            output = new FileOutputStream(path);
            byte[]data = new byte[1024];

            long total = 0;

            while((count = input.read(data))!=-1)
            {
                total+=count;
                String progress = ""+(int)((total*100)/lengthOfFile);
                Log.i(TAG,total+" "+progress);
                publishProgress(progress);
                output.write(data,0,count);

            }
            output.flush();
            output.close();
            input.close();
            connection.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
