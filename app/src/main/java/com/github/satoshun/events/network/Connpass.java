package com.github.satoshun.events.network;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/** Get data from conpass  */
public interface Connpass {
    @GET("/v1/event")
    Observable<ConnpassResponse> search();

    @GET("/v1/event")
    Observable<ConnpassResponse> search(@Query("keyword") String keyword);
}
