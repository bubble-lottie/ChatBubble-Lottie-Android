package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.ModifierContent;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.PtyData;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;

public class AnimatableTransform implements ModifierContent, ContentModel {
    @Nullable
    private final AnimatablePathValue anchorPoint;
    @Nullable
    private final AnimatableValue<PointF, PointF> position;
    @Nullable
    private final AnimatableScaleValue scale;
    @Nullable
    private final AnimatableFloatValue rotation;
    @Nullable
    private final AnimatableIntegerValue opacity;
    @Nullable
    private final AnimatableFloatValue skew;
    @Nullable
    private final AnimatableFloatValue skewAngle;

    // Used for repeaters
    @Nullable
    private final AnimatableFloatValue startOpacity;
    @Nullable
    private final AnimatableFloatValue endOpacity;

    public AnimatableTransform() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public AnimatableTransform(@Nullable AnimatablePathValue anchorPoint,
                               @Nullable AnimatableValue<PointF, PointF> position, @Nullable AnimatableScaleValue scale,
                               @Nullable AnimatableFloatValue rotation, @Nullable AnimatableIntegerValue opacity,
                               @Nullable AnimatableFloatValue startOpacity, @Nullable AnimatableFloatValue endOpacity,
                               @Nullable AnimatableFloatValue skew, @Nullable AnimatableFloatValue skewAngle) {
        this.anchorPoint = anchorPoint;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.opacity = opacity;
        this.startOpacity = startOpacity;
        this.endOpacity = endOpacity;
        this.skew = skew;
        this.skewAngle = skewAngle;
    }

    @Nullable
    public AnimatablePathValue getAnchorPoint() {
        return anchorPoint;
    }

    @Nullable
    public AnimatableValue<PointF, PointF> getPosition() {
        return position;
    }

    @Nullable
    public AnimatableScaleValue getScale() {
        return scale;
    }

    @Nullable
    public AnimatableFloatValue getRotation() {
        return rotation;
    }

    @Nullable
    public AnimatableIntegerValue getOpacity() {
        return opacity;
    }

    @Nullable
    public AnimatableFloatValue getStartOpacity() {
        return startOpacity;
    }

    @Nullable
    public AnimatableFloatValue getEndOpacity() {
        return endOpacity;
    }

    @Nullable
    public AnimatableFloatValue getSkew() {
        return skew;
    }

    @Nullable
    public AnimatableFloatValue getSkewAngle() {
        return skewAngle;
    }

    public TransformKeyframeAnimation createAnimation() {
        return new TransformKeyframeAnimation(this);
    }

    @Nullable
    @Override
    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return null;
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
        if (scale != null) for (Keyframe<ScaleXY> kf : scale.getKeyframes()) {
            if (kf.startValue != null && kf.endValue != null) {
                kf.startValue.set(
                        pd.to(pty, kf.startRawValue.getScaleX(), bmRawW, true, false),
                        pd.to(pty, kf.startRawValue.getScaleY(), bmRawH, false, false));
                kf.endValue.set(
                        pd.to(pty, kf.endRawValue.getScaleX(), bmRawW, true, false),
                        pd.to(pty, kf.endRawValue.getScaleY(), bmRawH, false, false));
            }
        }
    }

    private int bmRawW, bmRawH;

    public void setRawWH(int bmW, int bmH) {
        this.bmRawW = bmW;
        this.bmRawH = bmH;
    }

    @Override
    public void setPty(int pty) {
        this.pty = pty;
    }
}
