package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.MergePathsContent;
import com.airbnb.lottie.model.PtyData;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;


public class MergePaths implements ContentModel {

    public enum MergePathsMode {
        MERGE,
        ADD,
        SUBTRACT,
        INTERSECT,
        EXCLUDE_INTERSECTIONS;

        public static MergePathsMode forId(int id) {
            switch (id) {
                case 1:
                    return MERGE;
                case 2:
                    return ADD;
                case 3:
                    return SUBTRACT;
                case 4:
                    return INTERSECT;
                case 5:
                    return EXCLUDE_INTERSECTIONS;
                default:
                    return MERGE;
            }
        }
    }

    private final String name;
    private final MergePathsMode mode;
    private final boolean hidden;

    public MergePaths(String name, MergePathsMode mode, boolean hidden) {
        this.name = name;
        this.mode = mode;
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public MergePathsMode getMode() {
        return mode;
    }

    public boolean isHidden() {
        return hidden;
    }

    @Override
    @Nullable
    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        if (!drawable.enableMergePathsForKitKatAndAbove()) {
            Logger.warning("Animation contains merge paths but they are disabled.");
            return null;
        }
        return new MergePathsContent(this);
    }

    private int pty;

    @Override
    public void pty(@NonNull PtyData pd) {
    }

    @Override
    public void setPty(int pty) {
        this.pty = pty;
    }

    @Override
    public String toString() {
        return "MergePaths{" + "mode=" + mode + '}';
    }
}
