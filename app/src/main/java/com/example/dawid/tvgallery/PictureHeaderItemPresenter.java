package com.example.dawid.tvgallery;

import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.content.Context;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class PictureHeaderItemPresenter extends RowHeaderPresenter
{
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.pic_header_item, null);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o)
    {
        PictureHeaderItem iconHeaderItem = (PictureHeaderItem) ((ListRow) o).getHeaderItem();
        View rootView = viewHolder.view;

        ImageView iconView = rootView.findViewById(R.id.header_pic);
        int iconResId = iconHeaderItem.getPicture().getImageId();
        iconView.setImageDrawable(rootView.getResources().getDrawable(iconResId, null));
    }
}
