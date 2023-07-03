package com.blockchain.demo.util;

import java.io.File;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtil {

    public static void mkdirsIfNotExists(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
