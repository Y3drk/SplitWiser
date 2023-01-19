package com.splitwiser.splitwiserclient.data;

import com.splitwiser.splitwiserclient.model.category.Category;
import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface SplitWiserAPI {

    @GET("/api/users")
    Observable<List<User>> getUsers();

    @GET("/api/users/{id}")
    Observable<User> getUser(@Path("id") int id);

    @GET("/api/groups")
    Observable<List<Group>> getGroupsInfo();

    @GET("/api/groups/{id}")
    Observable<Group> getSingleGroupInfo(@Path("id") int groupId);

    @GET("/api/users/{id}/payments/{category}")
    Observable<List<Payment>> getUserPaymentsByCategory(@Path("id") int id, @Path("category") Category category);

    @GET("/api/groups/{id}/payments/{category}")
    Observable<List<Payment>> getGroupPaymentsByCategory(@Path("id") int groupId, @Path("category") Category category);

    @POST("/api/users/groups/{id}")
    Observable<User> postUser(@Body User user, @Path("id") int groupId);

    @POST("/api/groups")
    Observable<Group> postGroup(@Body Group group);

    @POST("/api/payments/groups/{groupId}")
    Observable<Payment> postPayment(@Body Payment payment, @Path("groupId") int groupId);
}
