package com.airbnb.lottie.model;

import android.util.Log;

import com.airbnb.lottie.utils.Utils;

public class PtyData {
    private int measXPx, measYPx; // 测量现数据
    private int animXPx, animYPx; // 动画原数据
    private int dx, dy;
    private float rateX, rateY;

    public void setMeasPx(int x, int y) {
        if (measXPx == x && measYPx == y) return;
        measXPx = x;
        measYPx = y;
        dealNum();
    }

    public void setAnimPx(int x, int y) {
        if (animXPx == x && animYPx == y) return;
        animXPx = x;
        animYPx = y;
        dealNum();
    }

    private void dealNum() {
        dx = measXPx <= animXPx ? 0 : measXPx - animXPx;
        dy = measYPx <= animYPx ? 0 : measYPx - animYPx;
        rateX = measXPx <= animXPx ? 1F : (1F * measXPx / animXPx);
        rateY = measYPx <= animYPx ? 1F : (1F * measYPx / animYPx);
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }

    public int getMeasXPx() {
        return measXPx;
    }

    public int getMeasYPx() {
        return measYPx;
    }

    // 16进制 前三位大小、后三位位置 zyx // 0;1+1;2-1;3*r;4+.5;5-.5;6补
    public float to(int pty, float raw, boolean isX, boolean isPosition) {
        return to(pty, raw, 0F, isX, isPosition);
    }

    public float to(int pty, float raw, float bmRaw, boolean isX, boolean isPosition) {
        int cut = (isX ? 0 : 4) + (isPosition ? 0 : 3 * 4);
        switch ((pty >>> cut) & 0xF) { // x大小
            case 1:
                raw += d(isX);
                break;
            case 2:
                raw -= d(isX);
                break;
            case 3:
                raw *= r(isX);
                break;
            case 4:
                raw += (d(isX) >> 1);
                break;
            case 5:
                raw -= (d(isX) >> 1);
                break;
            case 6: // raw为原图资源的缩放百分比，rawPx原显示宽
                if (bmRaw == 0) Log.e("lottie", "pty.to(): case 6, but bmRaw==0");
//                else raw = raw * (layRawPx + d(isX)) / layRawPx;
                else raw = raw + d(isX) / Utils.dpScale() / bmRaw;
                break;
            default:
                break;
        }
        return raw;
    }

    private float r(boolean isX) {
        return isX ? rateX : rateY;
    }

    private int d(boolean isX) {
        return isX ? dx : dy;
    }

}
