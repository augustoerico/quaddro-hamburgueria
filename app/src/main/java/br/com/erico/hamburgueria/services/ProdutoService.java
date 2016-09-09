package br.com.erico.hamburgueria.services;

import java.util.List;

import br.com.erico.hamburgueria.models.Produto;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by erico on 9/8/16.
 */
public interface ProdutoService {

    @GET("/api/v1/produtos")
    Call<List<Produto>> listProdutos();

    @GET("/api/v1/produtos/{id}")
    Call<Produto> getProduto();

}
