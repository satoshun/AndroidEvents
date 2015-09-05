package com.github.satoshun.events.network;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/** Get data from Atnd  */
public interface Atnd {
    @GET("/events")
    Observable<AtndResponse> search(@Query("ymd") List<String> ymds);

    @GET("/events")
    Observable<AtndResponse> search(@Query("keyword") String keyword,
            @Query("ymd") List<String> ymds);
}
