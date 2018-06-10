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
    //private float mUnselectedAlpha;
    private MainFragment mainFragment;

    public PictureHeaderItemPresenter(MainFragment mainFragment)
    {
        this.mainFragment = mainFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup)
    {
        //mUnselectedAlpha = viewGroup.getResources()
                //.getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);
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
        final PictureHeaderItem iconHeaderItem = (PictureHeaderItem) ((ListRow) o).getHeaderItem();
        View rootView = viewHolder.view;

        ImageView iconView = rootView.findViewById(R.id.header_pic);
        int iconResId = iconHeaderItem.getPictureId();
        iconView.setImageDrawable(rootView.getResources().getDrawable(iconResId, null));
       /* viewHolder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mainFragment.updateBackground(iconHeaderItem.getPictureId());
            }
        });*/
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        // no op
    }

    /*// TODO: TEMP - remove me when leanback onCreateViewHolder no longer sets the mUnselectAlpha,AND
    // also assumes the xml inflation will return a RowHeaderView
    @Override
    protected void onSelectLevelChanged(RowHeaderPresenter.ViewHolder holder)
    {
        // this is a temporary fix
        holder.view.setAlpha(mUnselectedAlpha + holder.getSelectLevel() *
                (1.0f - mUnselectedAlpha));
    }*/
}
