package com.techno.minilauncher.global;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.techno.minilauncher.LauncherApplication;
import com.techno.minilauncher.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.v4.app.ActivityCompat.startActivityForResult;

 
public class Global {

    public static final int MY_PERMISSIONS_REQUEST = 122;
    public static final int ALL_PERMISSIONS_REQUEST = 123;
    public static final int REQUEST_PERMISSION_SETTING = 124;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final int NOTIFICATION_ID = 500;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 501;

    // Function to check Internet Connectivity
    public static synchronized boolean isNetworkAvailable(Context context) {
        boolean isConnected = false;
        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }

        return isConnected;
    }


    public static void storePreference(String key, String value) {
        SharedPreferences.Editor editor = LauncherApplication.sharedPref
                .edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void storePreference(String key, int value) {
        SharedPreferences.Editor editor = LauncherApplication.sharedPref
                .edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void removePreference(String key) {
        SharedPreferences.Editor editor = LauncherApplication.sharedPref
                .edit();
        editor.remove(key);
        editor.commit();
    }

    public static void removePreferences(String keys[]) {
        SharedPreferences.Editor editor = LauncherApplication.sharedPref
                .edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.commit();
    }



    public static void clearPreferences() {
        SharedPreferences.Editor editor = LauncherApplication.sharedPref.edit();
        editor.clear();
        editor.commit();
    }

    public static void storePreference(String key, Boolean value) {
        SharedPreferences.Editor editor = LauncherApplication.sharedPref
                .edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static HashMap<String, String> getPreference(String[] keys) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        for (String key : keys) {
            parameters.put(key,
                    LauncherApplication.sharedPref.getString(key, null));
        }
        return parameters;
    }

    public static String getPreference(String key, String defValue) {
        return LauncherApplication.sharedPref.getString(key, defValue);
    }

    public static int getPreference(String key, Integer defValue) {
        return LauncherApplication.sharedPref.getInt(key, defValue);
    }

    public static Boolean getPreference(String key, Boolean defValue) {
        return LauncherApplication.sharedPref.getBoolean(key, defValue);
    }

    public static void removeAllPreferences() {

        //Global.removePreferences(new String[]{Constants.IS_LOGGED_IN});
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public static void activityTransition(Activity activity) {
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void activityBackTransition(Activity activity) {
        activity.overridePendingTransition(R.anim.hold, android.R.anim.fade_out);
    }


    // Function to hide keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }


    public static boolean checkPermission(final Context context) {
        int readExternalStorage = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
        int writeExternalStorage = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int camera = ContextCompat.checkSelfPermission(context, CAMERA);
        int coarseLocation = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineLocation = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION);
        int phoneCall = ContextCompat.checkSelfPermission(context, CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (readExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(READ_EXTERNAL_STORAGE);
        }
        if (writeExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(CAMERA);
        }
        if (fineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_FINE_LOCATION);

        }
        if (coarseLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (phoneCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CALL_PHONE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), ALL_PERMISSIONS_REQUEST);
            return false;
        }
        return true;
    }


    public static void permissionSettingView(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult((Activity) context, intent, REQUEST_PERMISSION_SETTING, null);
    }

    /* Function to set custom Font to Textviews from Assets */
    public static void setCustomFontTextview(Context context, TextView textView, String fontName) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
        textView.setTypeface(customFont);
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static void shareData(Context context, String text, Uri imageUri) {

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(shareIntent, "Share Data..."));

    }

//    public static File createImageFile(Context context) throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
//
//        File instanceRecordDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + context.getString(R.string.app_name) + File.separator + Constants.IMAGE_FOLDER_NAME);
//
//        if (!instanceRecordDirectory.exists()) {
//            instanceRecordDirectory.mkdirs();
//        }
//        File instanceRecord = new File(instanceRecordDirectory.getAbsolutePath() + File.separator + imageFileName + JPEG_FILE_SUFFIX);
//        if (!instanceRecord.exists()) {
//            try {
//                instanceRecord.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return instanceRecord;
//    }

    public static Uri getUriFromFile(Context context, String mCurrentPhotoPath) {
        return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(mCurrentPhotoPath));
    }

    public static float getDistance(String startLat, String startLong, String endLat, String endLong) {

        try {
            if (!TextUtils.isEmpty(startLat) && !TextUtils.isEmpty(startLong) && !TextUtils.isEmpty(endLat) && !TextUtils.isEmpty(endLong)) {
                Location startPoint = new Location("Source");
                startPoint.setLatitude(Double.parseDouble(startLat));
                startPoint.setLongitude(Double.parseDouble(startLong));

                Location endPoint = new Location("Destination");
                endPoint.setLatitude(Double.parseDouble(endLat));
                endPoint.setLongitude(Double.parseDouble(endLong));

                return startPoint.distanceTo(endPoint);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    /*Function for Check app in background or not*/
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

}
