package com.techno.minilauncher.model;

import java.io.Serializable;

/**
 * Created by thetaubuntu5 on 14/12/17.
 */

public class WallpaperMain implements Serializable {
    public String imgUrl;

    public WallpaperMain(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
