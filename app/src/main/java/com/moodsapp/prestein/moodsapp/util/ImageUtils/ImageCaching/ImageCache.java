package com.moodsapp.prestein.moodsapp.util.ImageUtils.ImageCaching;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;


import com.moodsapp.prestein.moodsapp.ui.activities.account_activities.for_home.Home_Activity;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.isExternalStorageRemovable;

public class ImageCache {
    Context context;
    private DiskLruCache mDiskLruCache;
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private LruCache<String, Bitmap> mMemoryCache;
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50; // 50MB
    private static final String DISK_CACHE_SUBDIR = "MoodsAppThumbNails";


    public ImageCache(Context context) {
        // Initialize memory cache
        this.context=context;
        // Initialize disk cache on background thread
        File cacheDir = getDiskCacheDir(context, DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheDir);

    }
    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            synchronized (mDiskCacheLock) {
                File cacheDir = params[0];
                mDiskLruCache = DiskLruCache.openCache(context,cacheDir, DISK_CACHE_SIZE);
                mDiskCacheStarting = false; // Finished initialization
                mDiskCacheLock.notifyAll(); // Wake any waiting threads
            }
            return null;
        }
    }


/*    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            final String imageKey = String.valueOf(params[0]);

            // Check disk cache in background thread
            Bitmap bitmap = null;
            try {
                bitmap = getBitmapFromDiskCache(imageKey);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (bitmap == null) { // Not found in disk cache
                // Process as normal
             //   final Bitmap bitmap1 = decodeSampledBitmapFromResource(getResources(), params[0], 100, 100));
            }

            // Add final bitmap to caches
            try {
                addBitmapToCache(imageKey, bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

    }*/

  public  Bitmap getImageFromDiskCache(String key) {
      return getBitmapFromDiskCache(key);
  }

   private boolean isThisBitmapCached(String key)   {
       return getBitmapFromDiskCache(key) != null;
   }

    public void addBitmapToCache(String key, Bitmap bitmap)  {
        // Add to memory cache as before
        /*if (getBitmapFromDiskCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
*/
        // Also add to disk cache
        synchronized (mDiskCacheLock) {
            if (mDiskLruCache != null && mDiskLruCache.get(key) == null) {
                mDiskLruCache.put(key, bitmap);
            }
        }
    }

    public Bitmap getBitmapFromDiskCache(String key) {
        synchronized (mDiskCacheLock) {
            // Wait while disk cache is started from background thread
            while (mDiskCacheStarting) {
                try {
                    mDiskCacheLock.wait();
                } catch (InterruptedException e) {
                    Toast.makeText(context,"getBitmapFromDiskCache  "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            if (mDiskLruCache != null) {
                return mDiskLruCache.get(key);
            }
        }
        return null;
    }

    // Creates a unique subdirectory of the designated app cache directory. Tries to use external
// but if not mounted, falls back on internal storage.
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }
}
