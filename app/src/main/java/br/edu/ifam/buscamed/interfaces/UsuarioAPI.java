package br.edu.ifam.buscamed.interfaces;

import java.util.List;

import br.edu.ifam.buscamed.dto.UsuarioDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioAPI {
    @GET("api/usuario")
    Call<List<UsuarioDTO>> getUsuario();

    @GET("api/usuario/{id}")
    Call<UsuarioDTO> getUsuario(@Path("id") Long id);

    @GET("api/usuario/login/{id}")
    Call<UsuarioDTO> getLogin(@Path("id") Long id);

    @POST("api/usuario")
    Call<UsuarioDTO> setUsuario(@Body UsuarioDTO usuarioDTO);

    @PUT("api/usuario/{id}")
    Call<UsuarioDTO> updateUsuario(@Path("id") Long id, @Body UsuarioDTO usuarioDTO);

    @DELETE("api/usuario/{id}")
    Call<UsuarioDTO> deleteUsuario(@Path("id") Long id);
}
