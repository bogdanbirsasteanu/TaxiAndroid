package com.starter.taxi.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.starter.taxi.R;

public class TaxiFloatingButton extends android.support.v7.widget.AppCompatButton {

    private Context mContext;

    public TaxiFloatingButton(Context context) {
        super(context);
        createView(context);
    }

    public TaxiFloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView(context);
    }

    public TaxiFloatingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createView(context);
    }

    private void createView(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.floating_button_widget_layout, null);
    }
}
