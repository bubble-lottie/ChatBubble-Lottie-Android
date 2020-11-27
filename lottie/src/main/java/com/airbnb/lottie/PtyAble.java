package com.airbnb.lottie;

import android.support.annotation.NonNull;

import com.airbnb.lottie.model.PtyData;

public interface PtyAble {

    void pty(@NonNull PtyData pd);

    void setPty(int pty);
}
