package com.github.satoshun.events.network;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/** Get data from Conpass  */
public interface Connpass {
    @GET("/v1/event")
    Observable<ConnpassResponse> search(
            @Query("ymd") List<String> ymds);

    @GET("/v1/event")
    Observable<ConnpassResponse> search(
            @Query("keyword") String keyword,
            @Query("ymd") List<String> ymds);
}
