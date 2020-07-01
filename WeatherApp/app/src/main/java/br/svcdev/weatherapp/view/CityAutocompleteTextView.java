package br.svcdev.weatherapp.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class CityAutocompleteTextView extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {

    private static final int MESSAGE_TEXT_CHANGED = 100;
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;

    private int mAutocompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
    private ProgressBar mIndicatorLoading;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            CityAutocompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };

    public CityAutocompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setIndicatorLoading(ProgressBar progressBar) {
        mIndicatorLoading = progressBar;
    }

    public void setAutocompleteDelay(int autocompleteDelay) {
        mAutocompleteDelay = autocompleteDelay;
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        if (mIndicatorLoading != null) {
            mIndicatorLoading.setVisibility(View.VISIBLE);
        }
        mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text),
                mAutocompleteDelay);
    }

    @Override
    public void onFilterComplete(int count) {
        if (mIndicatorLoading != null) {
            mIndicatorLoading.setVisibility(View.GONE);
        }
        super.onFilterComplete(count);
    }
}
