package br.edu.ifam.buscamed.interfaces;

import java.util.List;

import br.edu.ifam.buscamed.dto.FarmaciaDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FarmaciaAPI {

    @GET("api/farmacia")
    Call<List<FarmaciaDTO>> getFarmacia();

    @GET("api/farmacia/login/{id}")
    Call<FarmaciaDTO> getFarmaciaLogin(@Path("id") Long id);

    @GET("api/farmacia/{id}")
    Call<FarmaciaDTO> getFarmacia(@Path("id") Long id);

    @GET("api/farmacia/name/{nome}")
    Call<List<FarmaciaDTO>> getFarmaciaByNome(@Path("nome") String nome);

    @POST("api/farmacia")
    Call<FarmaciaDTO> setFarmacia(@Body FarmaciaDTO farmaciaDTO);

    @PUT("api/farmacia/{id}")
    Call<FarmaciaDTO> updateFarmacia(@Path("id") Long id, @Body FarmaciaDTO farmaciaDTO);

    @DELETE("api/farmacia/{id}")
    Call<FarmaciaDTO> deleteFarmacia(@Path("id") Long id);
}
