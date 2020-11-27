package com.airbnb.lottie.value;

import android.graphics.PointF;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.animation.Interpolator;

import com.airbnb.lottie.LottieComposition;

public class Keyframe<T> {
    private static final float UNSET_FLOAT = -3987645.78543923f;
    private static final int UNSET_INT = 784923401;

    @Nullable
    private final LottieComposition composition;
    @Nullable
    public final T startValue;
    @Nullable
    public T endValue;
    @Nullable
    public T startRawValue;
    @Nullable
    public T endRawValue;
    @Nullable
    public final Interpolator interpolator;
    public final float startFrame;
    @Nullable
    public Float endFrame;

//    private float startValueFloat = UNSET_FLOAT;
//    private float endValueFloat = UNSET_FLOAT;
//
//    private int startValueInt = UNSET_INT;
//    private int endValueInt = UNSET_INT;

    private float startProgress = Float.MIN_VALUE;
    private float endProgress = Float.MIN_VALUE;

    // Used by PathKeyframe but it has to be parsed by KeyFrame because we use a JsonReader to
    // deserialzie the data so we have to parse everything in order
    public PointF pathCp1 = null;
    public PointF pathCp2 = null;


    public Keyframe(@SuppressWarnings("NullableProblems") LottieComposition composition,
                    @Nullable T startValue, @Nullable T endValue,
                    @Nullable Interpolator interpolator, float startFrame, @Nullable Float endFrame) {
        this.composition = composition;
        this.startValue = startValue;
        this.endValue = endValue;
        this.interpolator = interpolator;
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        dealRawValue(startValue, endValue);
    }

    /**
     * Non-animated value.
     */
    public Keyframe(@SuppressWarnings("NullableProblems") T value) {
        composition = null;
        startValue = value;
        endValue = value;
        interpolator = null;
        startFrame = Float.MIN_VALUE;
        endFrame = Float.MAX_VALUE;
        dealRawValue(startValue, endValue);
    }

    @SuppressWarnings("unchecked")
    private void dealRawValue(@Nullable T startValue, @Nullable T endValue) {
        if (startValue instanceof PointF) {
            PointF p1 = new PointF();
            p1.set((PointF) startValue);
            startRawValue = (T) p1;
            if (endValue != null) {
                PointF p2 = new PointF();
                p2.set((PointF) endValue);
                endRawValue = (T) p2;
            }
        } else if (startValue instanceof ScaleXY) {
            startRawValue = (T) new ScaleXY((ScaleXY) startValue);
            if (endValue != null) endRawValue = (T) new ScaleXY((ScaleXY) endValue);
        }
    }

    @Nullable
    public LottieComposition getComposition() {
        return composition;
    }

    public float getStartProgress() {
        if (composition == null) {
            return 0f;
        }
        if (startProgress == Float.MIN_VALUE) {
            startProgress = (startFrame - composition.getStartFrame()) / composition.getDurationFrames();
        }
        return startProgress;
    }

    public float getEndProgress() {
        if (composition == null) {
            return 1f;
        }
        if (endProgress == Float.MIN_VALUE) {
            if (endFrame == null) {
                endProgress = 1f;
            } else {
                float startProgress = getStartProgress();
                float durationFrames = endFrame - startFrame;
                float durationProgress = durationFrames / composition.getDurationFrames();
                endProgress = startProgress + durationProgress;
            }
        }
        return endProgress;
    }

    public boolean isStatic() {
        return interpolator == null;
    }

    public boolean containsProgress(@FloatRange(from = 0f, to = 1f) float progress) {
        return progress >= getStartProgress() && progress < getEndProgress();
    }

    /**
     * Optimization to avoid autoboxing.
     */
    public float getStartValueFloat() {
//        if (startValueFloat == UNSET_FLOAT) {
//            startValueFloat = (float) (Float) startValue;
//        }
//        return startValueFloat;
        return (float) (Float) startValue;
    }

    /**
     * Optimization to avoid autoboxing.
     */
    public float getEndValueFloat() {
//        if (endValueFloat == UNSET_FLOAT) {
//            endValueFloat = (float) (Float) endValue;
//        }
//        return endValueFloat;
        return (float) (Float) endValue;
    }

    /**
     * Optimization to avoid autoboxing.
     */
    public int getStartValueInt() {
//        if (startValueInt == UNSET_INT) {
//            startValueInt = (int) (Integer) startValue;
//        }
//        return startValueInt;
        return (int) (Integer) startValue;
    }

    /**
     * Optimization to avoid autoboxing.
     */
    public int getEndValueInt() {
//        if (endValueInt == UNSET_INT) {
//            endValueInt = (int) (Integer) endValue;
//        }
//        return endValueInt;
        return (int) (Integer) endValue;
    }

    @Override
    public String toString() {
        return "Keyframe{" + "startValue=" + startValue +
                ", endValue=" + endValue +
                ", startFrame=" + startFrame +
                ", endFrame=" + endFrame +
                ", interpolator=" + interpolator +
                '}';
    }
}
