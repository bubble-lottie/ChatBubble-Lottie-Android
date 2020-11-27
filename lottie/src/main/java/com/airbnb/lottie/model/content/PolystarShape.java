package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import android.support.annotation.NonNull;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.PolystarContent;
import com.airbnb.lottie.model.PtyData;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.Keyframe;

public class PolystarShape implements ContentModel {
    public enum Type {
        STAR(1),
        POLYGON(2);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public static Type forValue(int value) {
            for (Type type : Type.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            return null;
        }
    }

    private final String name;
    private final Type type;
    private final AnimatableFloatValue points;
    private final AnimatableValue<PointF, PointF> position;
    private final AnimatableFloatValue rotation;
    private final AnimatableFloatValue innerRadius;
    private final AnimatableFloatValue outerRadius;
    private final AnimatableFloatValue innerRoundedness;
    private final AnimatableFloatValue outerRoundedness;
    private final boolean hidden;

    public PolystarShape(String name, Type type, AnimatableFloatValue points,
                         AnimatableValue<PointF, PointF> position,
                         AnimatableFloatValue rotation, AnimatableFloatValue innerRadius,
                         AnimatableFloatValue outerRadius, AnimatableFloatValue innerRoundedness,
                         AnimatableFloatValue outerRoundedness, boolean hidden) {
        this.name = name;
        this.type = type;
        this.points = points;
        this.position = position;
        this.rotation = rotation;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.innerRoundedness = innerRoundedness;
        this.outerRoundedness = outerRoundedness;
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public AnimatableFloatValue getPoints() {
        return points;
    }

    public AnimatableValue<PointF, PointF> getPosition() {
        return position;
    }

    public AnimatableFloatValue getRotation() {
        return rotation;
    }

    public AnimatableFloatValue getInnerRadius() {
        return innerRadius;
    }

    public AnimatableFloatValue getOuterRadius() {
        return outerRadius;
    }

    public AnimatableFloatValue getInnerRoundedness() {
        return innerRoundedness;
    }

    public AnimatableFloatValue getOuterRoundedness() {
        return outerRoundedness;
    }

    public boolean isHidden() {
        return hidden;
    }

    @Override
    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new PolystarContent(drawable, layer, this);
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
    }

    @Override
    public void setPty(int pty) {
        this.pty = pty;
    }

}
