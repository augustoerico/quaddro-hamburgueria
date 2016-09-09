package br.com.erico.hamburgueria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.erico.hamburgueria.controllers.HamburgueriaController;
import br.com.erico.hamburgueria.models.Produto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView lsvProdutos;
    private ArrayAdapter<Produto> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produtos);

        lsvProdutos = (ListView) findViewById(R.id.lsvProdutos);

        Call<List<Produto>> call = HamburgueriaController.getProdutoService().listProdutos();

        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    List<Produto> produtos = response.body();
                    for (Produto p : produtos) {
                        Log.i("MainActivity", p.getNome());
                    }
                    adapter = new ArrayAdapter<Produto>(
                            MainActivity.this, android.R.layout.simple_list_item_1, produtos);
                    lsvProdutos.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {

            }
        });

    }
}
