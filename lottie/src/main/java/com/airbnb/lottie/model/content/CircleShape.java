package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import android.support.annotation.NonNull;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.EllipseContent;
import com.airbnb.lottie.model.PtyData;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.Keyframe;

public class CircleShape implements ContentModel {
    private final String name;
    private final AnimatableValue<PointF, PointF> position;
    private final AnimatablePointValue size;
    private final boolean isReversed;
    private final boolean hidden;

    public CircleShape(String name, AnimatableValue<PointF, PointF> position,
                       AnimatablePointValue size, boolean isReversed, boolean hidden) {
        this.name = name;
        this.position = position;
        this.size = size;
        this.isReversed = isReversed;
        this.hidden = hidden;
    }

    @Override
    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new EllipseContent(drawable, layer, this);
    }

    public String getName() {
        return name;
    }

    public AnimatableValue<PointF, PointF> getPosition() {
        return position;
    }

    public AnimatablePointValue getSize() {
        return size;
    }

    public boolean isReversed() {
        return isReversed;
    }

    public boolean isHidden() {
        return hidden;
    }

    private int pty;

    @Override
    public void pty(@NonNull PtyData pd) {
        if (pty == 0) return;
        if (position != null) for (Keyframe<PointF> kf : position.getKeyframes()) {
            if (kf.startValue != null && kf.endValue != null) {
                kf.startValue.x = pd.to(pty, kf.startRawValue.x, true, true);
                kf.startValue.y = pd.to(pty, kf.startRawValue.y, false, true);
                kf.endValue.x = pd.to(pty, kf.endRawValue.x, true, true);
                kf.endValue.y = pd.to(pty, kf.endRawValue.y, false, true);
            }
        }
        if (size != null) for (Keyframe<PointF> kf : size.getKeyframes()) {
            if (kf.startValue != null && kf.endValue != null) {
                kf.startValue.set(
                        pd.to(pty, kf.startRawValue.x, true, false),
                        pd.to(pty, kf.startRawValue.y, false, false));
                kf.endValue.set(
                        pd.to(pty, kf.endRawValue.x, true, false),
                        pd.to(pty, kf.endRawValue.y, false, false));
            }
        }
    }

    @Override
    public void setPty(int pty) {
        this.pty = pty;
    }

}
