package br.com.erico.hamburgueria.controllers;

import br.com.erico.hamburgueria.services.ProdutoService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by erico on 9/8/16.
 */
public class HamburgueriaController {

    private static ProdutoService produtoService;

    public HamburgueriaController() {

    }

    public static ProdutoService getProdutoService() {
        setupProdutoService();
        return produtoService;
    }

    private static void setupProdutoService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://still-gorge-29810.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        produtoService = retrofit.create(ProdutoService.class);
    }

}
