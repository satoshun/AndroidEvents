package com.github.satoshun.events.network;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/** Get data from Zusaar  */
public interface Zusaar {
    @GET("/event/")
    Observable<ZusaarResponse> search(@Query("ymd") List<String> ymds);

    @GET("/event/")
    Observable<ZusaarResponse> search(@Query("keyword") String keyword,
            @Query("ymd") List<String> ymds);
}
