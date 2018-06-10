package com.example.dawid.tvgallery;

import android.support.v17.leanback.widget.HeaderItem;

public class PictureHeaderItem extends HeaderItem
{
    private int pictureId;

    public PictureHeaderItem(long id, String name, int pictureId)
    {
        super(id, name);
        this.pictureId = pictureId;
    }

    public int getPictureId()
    {
        return pictureId;
    }
}
