package br.edu.ifam.buscamed.interfaces;

import java.util.List;

import br.edu.ifam.buscamed.dto.VendaInputDTO;
import br.edu.ifam.buscamed.dto.VendaOutputDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VendaAPI {

    @GET("api/venda/farmacia/{id}")
    Call<List<VendaOutputDTO>> getVendaFarmacia(@Path("id") Long id);


    @GET("api/venda/{id}")
    Call<VendaInputDTO> getVendaById(@Path("id") Long id);

    @GET("api/venda/user/{id}")
    Call<List<VendaOutputDTO>> getVendaUser(@Path("id") Long id);

    @POST("api/venda")
    Call<VendaInputDTO> setVenda(@Body VendaInputDTO vendaDTO);

    @PUT("api/venda/{id}")
    Call<VendaOutputDTO> updateVenda(@Path("id") Long id, @Body VendaInputDTO vendaInputDTO);

}
