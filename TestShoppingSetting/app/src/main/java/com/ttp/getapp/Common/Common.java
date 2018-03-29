package com.ttp.getapp.Common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.ttp.getapp.Model.Request;
import com.ttp.getapp.Model.User;
import com.ttp.getapp.Remote.APIService;
import com.ttp.getapp.Remote.FCMRetroficClient;
import com.ttp.getapp.Remote.IGeoCoordinates;
import com.ttp.getapp.Remote.RetrofitClient;

/**
 * Created by 0047TiTANplateform_ on 2018-01-31.
 */

public class Common {

    public static User currentuser;
    public static Request currentRequest;
    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final int PICK_NAME_REQUEST = 71;
    public static final String baseUrl = "https://maps.googleapis.com";
    public static final String fcmUrl = "https://fcm.googleapis.com/";

    public static String convertCodeToStatus(String code){
        if(code.equals("0")) return "Placed";
        else if(code.equals("1")) return "On my way";
        else return "Shipped";
    }

    public static APIService getFCMClient(){
        return FCMRetroficClient.getClient(fcmUrl).create(APIService.class);
    }

    public static IGeoCoordinates getGeoCodeService(){
        return RetrofitClient.getClient(baseUrl).create(IGeoCoordinates.class);
    }


    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight){
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth/(float)bitmap.getWidth();
        float scaleY = newWidth/(float)bitmap.getHeight();
        float pivotX = 0, pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return  scaledBitmap;

    }

}
