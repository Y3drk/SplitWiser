package com.splitwiser.splitwiserclient.data;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.splitwiser.splitwiserclient.model.category.Category;
import com.splitwiser.splitwiserclient.model.group.Group;
import com.splitwiser.splitwiserclient.model.payment.Payment;
import com.splitwiser.splitwiserclient.model.user.User;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class DataService {

    static final String BASE_URL = "http://localhost:8080/";
    private final SplitWiserAPI splitWiserAPI;


    public DataService() {
        ObjectMapper mapper = JsonMapper.builder().build();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        this.splitWiserAPI = retrofit.create(SplitWiserAPI.class);
    }

    public Observable<Group> getGroupsInfo() {
        return this.splitWiserAPI.getGroupsInfo().flatMap(Observable::fromIterable);
    }

    public Observable<Payment> getGroupPaymentsByCategory(int groupId, Category category) {
        return this.splitWiserAPI.getGroupPaymentsByCategory(groupId, category).flatMap(Observable::fromIterable);
    }

    public Observable<User> createUser(User user) {
        return this.splitWiserAPI.postUser(user, user.getGroup().getId());
    }

    public Observable<Group> createGroup(Group group) {
        return this.splitWiserAPI.postGroup(group);
    }

    public Observable<Payment> createPayment(Payment payment, int group_id) {
        return this.splitWiserAPI.postPayment(payment, group_id);
    }

    public Observable<Group> getSingleGroupInfo(int groupId) {
        return this.splitWiserAPI.getSingleGroupInfo(groupId);
    }

}
