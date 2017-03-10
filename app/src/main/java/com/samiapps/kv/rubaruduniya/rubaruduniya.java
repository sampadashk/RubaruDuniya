package com.samiapps.kv.rubaruduniya;

/**
 * Created by KV on 7/3/17.
 */

public class rubaruduniya {
    private String title;
    private String content;
    private String photoUrl;
    private Boolean saved;
    private int i=1;
    public rubaruduniya()
    {

    }
   public rubaruduniya(Boolean saved,String title,String content,String photoUrl,int i)
   {
       this.saved=saved;
       this.title=title;
       this.content=content;
       this.photoUrl=photoUrl;

       this.i=i;
   }
    public void setTitle(String title)
    {
        this.title=title;

    }
    public String getTitle()
    {
        return title;

    }
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
