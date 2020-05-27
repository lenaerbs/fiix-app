package com.fiix.app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FiixService {
        @GET("api/Calendar/{code}")
        Call<FiixCalendar> getOnlineCalendar(@Path(value="code", encoded = true)String code);
}
