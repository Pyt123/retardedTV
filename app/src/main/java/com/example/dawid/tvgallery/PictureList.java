/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.dawid.tvgallery;

import java.util.ArrayList;
import java.util.List;

public final class PictureList
{
    private static List<Picture> pictures;

    public static List<Picture> getPictures()
    {
        if (pictures == null)
        {
            pictures = setupPictures();
        }
        return pictures;
    }

    private static List<Picture> setupPictures()
    {
        int imageIds[] = {
                R.drawable.stock1,
                R.drawable.stock2,
                R.drawable.stock3,
                R.drawable.stock4,
                R.drawable.stock5
        };

        pictures = new ArrayList<>(imageIds.length);

        for (int i = 0; i < imageIds.length; i++)
        {
            pictures.add(new Picture(imageIds[i]));
        }

        return pictures;
    }
}