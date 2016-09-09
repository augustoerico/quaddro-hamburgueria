package br.com.erico.hamburgueria.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.erico.hamburgueria.R;
import br.com.erico.hamburgueria.models.Produto;

/**
 * Created by erico on 9/8/16.
 */
public class ProdutoAdapter extends ArrayAdapter<Produto> {

    private static Integer LAYOUT = R.layout.produto_detail;

    private Context context;
    private List<Produto> produtos;
    private ProdutoHolder holder;

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        super(context, LAYOUT, produtos);

        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(LAYOUT, parent, false);

            holder = new ProdutoHolder();
            holder.txvNome = (TextView) row.findViewById(R.id.txvNome);
            holder.imvFoto = (ImageView) row.findViewById(R.id.imvFoto);

            row.setTag(holder);

        } else {

            holder = (ProdutoHolder) row.getTag();

        }

        Produto produto = produtos.get(position);
        holder.txvNome.setText(produto.getNome());
        holder.imvFoto.setImageResource(produto.getImage(context));

        return row;

    }

    static class ProdutoHolder {

        TextView txvNome;
        ImageView imvFoto;

    }

}
