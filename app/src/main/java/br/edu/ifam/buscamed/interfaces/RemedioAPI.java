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

    @GET("/farmacia")
    Call<List<RemedioDTO>> getRemedio();

    @GET("/farmacia/{id}")
    Call<RemedioDTO> getRemedio(@Path("id") Long id);

    @POST("/farmacia")
    Call<RemedioDTO> setRemedio(@Body RemedioDTO remedioDTO);

    @PUT("/farmacia/{id}")
    Call<RemedioDTO> updateRemedio(@Path("id") Long id, @Body RemedioDTO remedioDTO);

    @DELETE("/farmacia/{id}")
    Call<RemedioDTO> deleteRemedio(@Path("id") Long id);
}
