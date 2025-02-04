package br.edu.ifam.buscamed.interfaces;

import java.util.List;

import br.edu.ifam.buscamed.dto.LoginDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginAPI {
    @GET("api/login")
    Call<List<LoginDTO>> getLogin();

    @GET("api/login/{id}")
    Call<LoginDTO> getLogin(@Path("id") Long id);

    @POST("api/login")
    Call<LoginDTO> setLogin(@Body LoginDTO loginDTO);

    @PUT("api/login/{id}")
    Call<LoginDTO> updateLogin(@Path("id") Long id, @Body LoginDTO loginDTO);

    @DELETE("api/login/{id}")
    Call<LoginDTO> deleteLogin(@Path("id") Long id);

    @POST("api/login/autenticar")
    Call<LoginDTO> autenticar(@Body LoginDTO loginDTO);
}
