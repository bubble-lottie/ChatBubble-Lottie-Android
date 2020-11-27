package com.airbnb.lottie.model.content;

import android.support.annotation.NonNull;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.model.PtyData;
import com.airbnb.lottie.model.layer.BaseLayer;

import java.util.Arrays;
import java.util.List;

public class ShapeGroup implements ContentModel {
    private final String name;
    private final List<ContentModel> items;
    private final boolean hidden;

    public ShapeGroup(String name, List<ContentModel> items, boolean hidden) {
        this.name = name;
        this.items = items;
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public List<ContentModel> getItems() {
        return items;
    }

    public boolean isHidden() {
        return hidden;
    }

    @Override
    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new ContentGroup(drawable, layer, this);
    }

    private int pty;

    @Override
    public void pty(@NonNull PtyData pd) {
        if (items != null) for (ContentModel cm : items) cm.pty(pd);
    }

    @Override
    public void setPty(int pty) {
        this.pty = pty;
    }

    @Override
    public String toString() {
        return "ShapeGroup{" + "name='" + name + "\' Shapes: " + Arrays.toString(items.toArray()) + '}';
    }
}
