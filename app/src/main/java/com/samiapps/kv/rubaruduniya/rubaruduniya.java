package com.samiapps.kv.rubaruduniya;

import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;

import java.io.Serializable;

/**
 * Created by KV on 7/3/17.
 */

public class rubaruduniya implements Serializable {
    private String title;
    private String content;
    private String photoUrl;
    private Boolean saved;

    public rubaruduniya()
    {

    }



    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public rubaruduniya(Boolean saved, String title, String content, String photoUrl)
   {
       this.saved=saved;
       this.title=title;
       this.content=content;
       this.photoUrl=photoUrl;


   }
    public void setTitle(String title)
    {
        this.title=title;

    }
    public String getTitle()
    {
        return title;

    }
    /*public String getImage()
    {
        return photoUrl;
    }
    */
    public Boolean getSaved()
    {
        return saved;
    }
    public void setContent(String content)
    {
        this.content=content;
    }
    public String getContent()
    {
        return content;
    }


    public void setPhotoUrl(java.lang.String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public String getPhotoUrl()
    {
        return photoUrl;
    }

}
