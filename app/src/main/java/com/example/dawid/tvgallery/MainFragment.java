package com.example.dawid.tvgallery;

import java.util.List;

import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class MainFragment extends BrowseFragment
{
    private DisplayMetrics metrics;
    private BackgroundManager backgroundManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        setupBackgroundManagerAndMetrics();
        setupUIElements();
        setupHeaderPictures();
        setupEventListeners();
    }

    private void setupBackgroundManagerAndMetrics()
    {
        backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    private void setupUIElements()
    {
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        setHeaderPresenterSelector(new PresenterSelector()
        {
            @Override
            public Presenter getPresenter(Object o)
            {
                return new PictureHeaderItemPresenter();
            }
        });
    }

    private void setupHeaderPictures()
    {
        List<Picture> pictures = PictureList.getPictures();

        ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        PictureHeaderItemPresenter cardPresenter = new PictureHeaderItemPresenter();

        for (int i = 0; i < pictures.size(); i++)
        {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            PictureHeaderItem header = new PictureHeaderItem(i, "", pictures.get(i));
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }

        setAdapter(mRowsAdapter);
    }

    private void setupEventListeners()
    {
        setOnItemViewSelectedListener(new OnItemViewSelectedListener()
        {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                       RowPresenter.ViewHolder rowViewHolder, Row row)
            {
                HeaderItem headerItem = row.getHeaderItem();
                if (headerItem instanceof PictureHeaderItem)
                {
                    PictureHeaderItem pictureHeaderItem = (PictureHeaderItem) headerItem;
                    updateBackground(pictureHeaderItem.getPicture().getImageId());
                }
            }
        });
    }

    protected void updateBackground(int picId)
    {
        Glide.with(getActivity())
                .load(picId)
                .into(new SimpleTarget<GlideDrawable>(metrics.widthPixels, metrics.heightPixels)
                {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation)
                    {
                        backgroundManager.setDrawable(resource);
                    }
                });
    }
}