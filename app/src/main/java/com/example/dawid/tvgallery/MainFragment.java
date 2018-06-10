package com.example.dawid.tvgallery;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class MainFragment extends BrowseFragment
{
    private static final String TAG = "MainFragment";

    private static final int BACKGROUND_UPDATE_DELAY = 100;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;

    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private int mBackgroundUri;
    private BackgroundManager mBackgroundManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        prepareBackgroundManager();

        setupUIElements();

        loadRows();

        setupEventListeners();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (null != mBackgroundTimer)
        {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }

    private void loadRows()
    {
        List<Movie> list = MovieList.setupMovies();

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        PictureHeaderItemPresenter cardPresenter = new PictureHeaderItemPresenter(this);

        for (int i = 0; i < list.size(); i++)
        {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            //listRowAdapter.add(list.get(j % 5));
            PictureHeaderItem header = new PictureHeaderItem(i, "", list.get(i).getBackgroundImageUrl());
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }


        //GridItemPresenter mGridPresenter = new GridItemPresenter();
        //ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        //gridRowAdapter.add(getResources().getString(R.string.grid_view));
        //gridRowAdapter.add(getString(R.string.error_fragment));
        //gridRowAdapter.add(getResources().getString(R.string.personal_settings));

        setAdapter(mRowsAdapter);
    }

    private void prepareBackgroundManager()
    {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements()
    {
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        final MainFragment mainFragment = this;
        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new PictureHeaderItemPresenter(mainFragment);
            }
        });
    }

    private void setupEventListeners()
    {
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    protected void updateBackground(int uri)
    {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .into(new SimpleTarget<GlideDrawable>(width, height)
                {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation)
                    {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
        mBackgroundTimer.cancel();
    }

    private void startBackgroundTimer()
    {
        if (null != mBackgroundTimer)
        {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener
    {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row)
        {
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener
    {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row)
        {
            HeaderItem headerItem = row.getHeaderItem();
            if (headerItem instanceof PictureHeaderItem)
            {
                PictureHeaderItem pictureHeaderItem = (PictureHeaderItem) headerItem;
                mBackgroundUri = pictureHeaderItem.getPictureId();
                startBackgroundTimer();
            }
        }
    }

    private class UpdateBackgroundTask extends TimerTask
    {
        @Override
        public void run()
        {
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    updateBackground(mBackgroundUri);
                }
            });
        }
    }

    /*private class GridItemPresenter extends Presenter
    {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent)
        {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item)
        {
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder)
        {
        }
    }*/
}
