package com.moodsapp.prestein.moodsapp.resourceshelper;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Prestein on 9/14/2017.
 */

public class ResourcesHelper extends Resources {
    /**
     * @param assets
     * @param metrics
     * @param config
     * @deprecated
     */
    public ResourcesHelper(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }
}
