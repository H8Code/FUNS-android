package org.h8code.funs.funs;

import android.net.Uri;

/**
 * Created by belial on 06.03.16.
 */
public class FunsContentProviderUri {
    private static final String content__ = "content://";
    private static final String delim__ = "/";

    public static final String AUTHORITY = "org.h8code.funs.funs";
    public static final String SCHEDULES_PATH = "schedules";

    public static final Uri SCHEDULES_URI = Uri.parse(
            content__ +
                    AUTHORITY +
                    delim__ +
                    SCHEDULES_PATH);
}
