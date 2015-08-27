package com.github.satoshun.events.network;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/** Get data from Atnd  */
public interface Atnd {
    @GET("/events")
    Observable<AtndResponse> search();

    @GET("/events")
    Observable<AtndResponse> search(@Query("keyword") String keyword);
}
