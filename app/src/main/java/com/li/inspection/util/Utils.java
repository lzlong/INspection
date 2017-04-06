package com.li.inspection.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.li.inspection.constant.Constants;
import com.li.inspection.entity.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by long on 17-1-10.
 */

public class Utils {
    static Toast toast = null;

    public static void showToast(Context context, String str) {
        // Toast.makeText(context, str, Toast.LENGTH_SHORT).show();;
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    public static void logI(String str) {
        Log.i(Constants.TAG, str);
    }

    public static void logD(String str) {
        Log.d(Constants.TAG, str);
    }

    public static void logE(String str) {
        Log.e(Constants.TAG, str);
    }

    /**
     * 判断字符串是否为空 为空 返回 true
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0 || cs.equals("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^1[3|4|5|8]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String getTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(System.currentTimeMillis());
        String time=format.format(date);
        return time;
    }
    public static Bitmap getDiskBitmap(String pathString)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(pathString);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        return bitmap;
    }
    /**
     * dip转pix
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    public static View getContentView(Activity ac){
        ViewGroup view = (ViewGroup)ac.getWindow().getDecorView();
        FrameLayout content = (FrameLayout)view.findViewById(android.R.id.content);
        return content.getChildAt(0);
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static JSONObject parseResponse(HttpResponse response){
        HttpEntity httpEntity;
        InputStream stream = null;
        StringBuffer sb = new StringBuffer();
        JSONObject jsonObject = null;
        try{
            if (response != null && response.getStatusLine() != null &&
                    response.getStatusLine().getStatusCode() == 200) {
                // 响应的实体，代表接受http的消息，服务器返回的消息都在Entity
                httpEntity = response.getEntity();
                // 通过httpEntity可以得到流
                stream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(stream));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String json = PullUtils.parseXMLToJSON(sb.toString());
                if (isNotBlank(json)){
                    jsonObject = new JSONObject(json);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static void parseUser(JSONObject jsonObject, SharedPreferences preferences) {
        User user = User.getInstance();
        SharedPreferences.Editor edit = preferences.edit();
        user.setId(jsonObject.optString("id"));
        user.setName(jsonObject.optString("name"));
        user.setPhone(jsonObject.optString("phone"));
        user.setPhotoUrl(jsonObject.optString("photoUrl"));
        edit.putString("userId", user.getId());
        edit.putString("userName", user.getName());
        edit.putString("userPhone", user.getPhone());
        edit.putString("userPhotoUrl", user.getPhotoUrl());
        edit.commit();

    }
    public static void getUserData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("userData", 0);
        User user = User.getInstance();
        SharedPreferences.Editor edit = preferences.edit();
        user.setId(preferences.getString("userId", ""));
        user.setName(preferences.getString("userName", ""));
        user.setPhone(preferences.getString("userPhone", ""));
        user.setPhotoUrl(preferences.getString("userPhotoUrl", ""));

    }
}
