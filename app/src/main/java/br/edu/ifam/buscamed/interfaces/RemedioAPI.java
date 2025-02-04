package br.edu.ifam.buscamed.interfaces;

import java.util.List;

import br.edu.ifam.buscamed.dto.FarmaciaDTO;
import br.edu.ifam.buscamed.dto.RemedioDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RemedioAPI {

    @GET("api/remedio")
    Call<List<RemedioDTO>> getRemedio();

    @GET("api/remedio/{id}")
    Call<RemedioDTO> getRemedio(@Path("id") Long id);

    @GET("api/remedio/name/{nome}")
    Call<List<RemedioDTO>> getRemedioByNome(@Path("nome") String nome);

    @GET("api/remedio/farmacia/{id}")
    Call<List<RemedioDTO>> getRemedioByFarmacia(@Path("id") Long id);

    @POST("api/remedio")
    Call<RemedioDTO> setRemedio(@Body RemedioDTO remedioDTO);

    @PUT("api/remedio/{id}")
    Call<RemedioDTO> updateRemedio(@Path("id") Long id, @Body RemedioDTO remedioDTO);

    @DELETE("api/remedio/{id}")
    Call<RemedioDTO> deleteRemedio(@Path("id") Long id);
}
