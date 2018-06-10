package com.example.dawid.tvgallery;

import android.support.v17.leanback.widget.HeaderItem;

public class PictureHeaderItem extends HeaderItem
{
    private Picture picture;

    public PictureHeaderItem(long id, String name, Picture picture)
    {
        super(id, name);
        this.picture = picture;
    }

    public Picture getPicture()
    {
        return picture;
    }
}
