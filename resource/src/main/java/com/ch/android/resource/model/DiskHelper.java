package com.ch.android.resource.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ch.android.resource.util.LogUtils;
import com.google.gson.Gson;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DiskHelper {

    private static final String TAG = "DiskHelper";

    private static DiskHelper sDefault;

    private Context mContext;

    private DiskHelper(Context context) {
        mContext = context;
    }

    public static DiskHelper getDefault(Context context) {
        if (sDefault == null) {
            synchronized (DiskHelper.class) {
                if (sDefault == null) {
                    sDefault = new DiskHelper(context);
                }
            }
        }
        return sDefault;
    }

    public Bitmap readBitmap(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        FileInputStream fos = null;
        try {
            fos = new FileInputStream(file);
            return BitmapFactory.decodeStream(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public void saveBitmap(String path, Bitmap bitmap) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LogUtils.e(TAG, "saveBitmap >> error:" + e.getClass().getSimpleName());
                e.printStackTrace();
                return;
            }
        }

        File tmpFile = new File(file.getParent(), file.getName() + ".tmp");
        if (!tmpFile.exists()) {
            try {
                tmpFile.createNewFile();
            } catch (IOException e) {
                LogUtils.e(TAG, "saveBitmap >> error:" + e.getClass().getSimpleName());
                e.printStackTrace();
                return;
            }
        }

        boolean success = true;

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(tmpFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
        } catch (FileNotFoundException e) {
            success = false;
            e.printStackTrace();
        } catch (IOException e) {
            success = false;
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    success = false;
                    e.printStackTrace();
                }
            }
        }

        if (success) {
            // 临时文件改名字
            tmpFile.renameTo(file);
        } else {
            // 删除文件和临时文件
            file.delete();
            tmpFile.delete();
        }
    }

    public <T> T readObject(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return (T) ois.readObject();
        } catch (FileNotFoundException e) {
            LogUtils.e(TAG, "writeObject >> error:" + e.getClass().getSimpleName());
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.e(TAG, "writeObject >> error:" + e.getClass().getSimpleName());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LogUtils.e(TAG, "writeObject >> error:" + e.getClass().getSimpleName());
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public <T extends Serializable> void writeObject(T object, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LogUtils.e(TAG, "writeObject >> createNewFile failed.");
                e.printStackTrace();
            }
        }

        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (FileNotFoundException e) {
            LogUtils.e(TAG, "writeObject >> error:" + e.getClass().getSimpleName());
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.e(TAG, "writeObject >> error:" + e.getClass().getSimpleName());
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeObjectWithGson(Object o, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LogUtils.e(TAG, "writeObjectWithGson >> createNewFile failed.");
                e.printStackTrace();
            }
        }

        Gson gson = new Gson();
        String str = gson.toJson(o);

        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(str);
            fw.flush();
        } catch (IOException e) {
            LogUtils.e(TAG, "writeObjectWithGson >> error:" + e.getClass().getSimpleName());
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> T readObjectWithGson(String filePath, Class<T> classOfT) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s).append('\n');
            }
        } catch (FileNotFoundException e) {
            LogUtils.e(TAG, "readObjectWithGson >> error:" + e.getClass().getSimpleName());
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.e(TAG, "readObjectWithGson >> error:" + e.getClass().getSimpleName());
            e.printStackTrace();
        }

        String jsonStr = sb.toString();

        Gson gson = new Gson();
        return gson.fromJson(jsonStr, classOfT);
    }
}
