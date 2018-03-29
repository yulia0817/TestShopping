package com.ttp.shoppingapp.Remote;


import com.ttp.shoppingapp.Model.MyResponse;
import com.ttp.shoppingapp.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by 0047TiTANplateform_ on 2018-03-21.
 */

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAqDUtPMQ:APA91bF6OtZuIE8yZYIvvSHapU5qQOPsyDwRtgRhl6rI0Fw4UmFF_nufUbQjHYUZipGBqdIacCkUwpJLlz6eHiPnYvhWxAXxSkYZVX1INKE_1PCrvUcbd85GKjGqBv9kgdV2VjW-30x_"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
