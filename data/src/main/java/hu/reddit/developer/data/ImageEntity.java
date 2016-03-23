package hu.reddit.developer.data;

/**
 * Created by László Gálosi on 23/03/16
 */
/*
 * Copyright (C) 2015 Laszlo Galosi, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 * All information contained herein is, and remains the property of Dr. Krisztian Balazs.
 * The intellectual and technical concepts contained herein are proprietary to Dr. Krisztian
 * Balazs and may be covered by U.S. and Foreign Patents, pending patents, and are protected
 * by trade secret or copyright law. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is obtained from
 * Laszlo Galosi.
 */

import com.google.gson.annotations.SerializedName;

/**
 * Generated code from ObjectFactory template.
 * Created by László Gálosi on  Mar 23, 2016 11:50:28 AM
 */
public class ImageEntity extends BasicEntity {

    //{"url":"https://i.redditmedia.com/AqBwkxhdXu-3XVSHaclkvEj6lXGqEDL9oXDiEed8l2s
    // .jpg?s=d61a3f9e869c01d615714d47e58918a9","width":600,"height":355}
    @SerializedName("url") public String url;
    @SerializedName("width") public int width;
    @SerializedName("height") public int height;

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("ImageEntity={");
        sb.append(super.toString());
        sb.append(", \nurl=").append(url);
        sb.append(", \nwidth=").append(width);
        sb.append(", \nheight=").append(height);
        sb.append("}");
        return sb.toString();
    }
}
