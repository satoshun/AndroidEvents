package com.github.satoshun.events.network;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/** Get data from Zusaar  */
public interface Zusaar {
    @GET("/event/")
    Observable<ZusaarResponse> search();

    @GET("/event/")
    Observable<ZusaarResponse> search(@Query("keyword") String keyword);
}
