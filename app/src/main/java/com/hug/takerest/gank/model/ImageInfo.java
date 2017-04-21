package com.hug.takerest.gank.model;

/**
 * Created by HStan on 2017/3/30.
 */

public class ImageInfo {

    /**
     * format : jpeg
     * width : 1080
     * height : 1080
     * colorModel : ycbcr
     */

    private String format;
    private int width;
    private int height;
    private String colorModel;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColorModel() {
        return colorModel;
    }

    public void setColorModel(String colorModel) {
        this.colorModel = colorModel;
    }
}
