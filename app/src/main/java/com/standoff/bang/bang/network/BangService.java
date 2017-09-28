package com.standoff.bang.bang.network;

import com.standoff.bang.bang.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by kelvinhanma on 9/28/17.
 */

public interface BangService {
    @GET("rooms")
    Call<List<Room>> listRooms();

    @POST("join")
    Call<Room> joinRoom(@Body User user);
}
