package com.github.satoshun.events.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    public Connpass provideConnpass() {
        RestAdapter.Builder builder = restAdapter("http://connpass.com/api/")
                .setConverter(new GsonConverter(gson("yyyy-MM-dd'T'HH:mm:ssZ")));
        return builder.build()
                .create(Connpass.class);
    }

    @Singleton
    @Provides
    public Atnd provideAtnd() {
        RestAdapter.Builder builder = restAdapter("http://api.atnd.org/")
                .setConverter(new GsonConverter(gson("yyyy-MM-dd'T'HH:mm:ss.SSSZ")));
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addQueryParam("format", "json");
            }
        });
        return builder.build().
                create(Atnd.class);
    }

    @Singleton
    @Provides
    public Zusaar provideZusaar() {
        RestAdapter.Builder builder = restAdapter("http://www.zusaar.com/api/")
                .setConverter(new GsonConverter(gson("yyyy-MM-dd'T'HH:mm:ssZ")));
        return builder.build()
                .create(Zusaar.class);
    }

    private RestAdapter.Builder restAdapter(final String endpoint) {
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL);
    }

    private Gson gson(String dateFormat) {
        Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat)
                .create();
        return gson;
    }
}
