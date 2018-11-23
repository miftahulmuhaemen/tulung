package com.unlam.developerstudentclub.tulung.Utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class NpaLiniearLayoutManager extends LinearLayoutManager {

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    public NpaLiniearLayoutManager(Context context) {
        super(context);
    }

    public NpaLiniearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NpaLiniearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
