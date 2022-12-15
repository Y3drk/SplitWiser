package com.splitwiser.splitwiserclient.data;

import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.user.User;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface SplitWiserAPI {

    @GET("/api/users")
    Observable<List<User>> getUsers();

    @GET("/api/user/{id}")
    Observable<User> getUser(@Path("id") int id);

    @GET("/api/groups")
    Observable<List<Group>> getGroupsInfo();

    @POST("/api/user/group/{id}")
    Observable<User> postUser(@Body User user, @Path("id") int groupId);

    @POST("/api/group")
    Observable<Group> postGroup(@Body Group group);
}
