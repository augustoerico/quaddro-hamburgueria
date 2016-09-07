package br.com.erico.hamburgueria;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mobile on 06/09/2016.
 */
public class GravarProdutos {

    private ProgressDialog progressDialog;
    private Context context;

    public GravarProdutos(Context context) {

        this.context = context;

        InputStream stream = context.getResources().openRawResource(R.raw._produtos);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer stringBuffer = new StringBuffer();
            String line = new String();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }

            reader.close();
            JSONArray jsonArray = new JSONArray(stringBuffer.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                onSendData(jsonArray.getJSONObject(i));
            }

        } catch (FileNotFoundException e) {
            Log.e("GravarProdutos", e.getMessage());
        } catch (IOException e) {
            Log.e("GravarProdutos", e.getMessage());
        } catch (JSONException e) {
            Log.e("GravarProdutos", e.getMessage());
        }

    }

    private void onSendData(final JSONObject jsonObject) throws JSONException {

        // Elaborando o async task
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Sending hamburguer");
                progressDialog.setMessage("Wait for it...");
                progressDialog.setCancelable(true);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {

                // The bicho is going to get it
                try {

                    // 1 segundo para respirar (WTF)
                    Thread.sleep(1000);

                    // Recuperando os dados enviados
                    InputStream stream = null; // recuperar e tratar a informação

                    URL url = new URL("https://still-gorge-29810.herokuapp.com/api/v1/produtos");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setRequestProperty("Content-Type", "application/json");


                    // Executar a url
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(jsonObject.toString().getBytes());
                    outputStream.flush();

                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    Log.i("GravarProdutos", "Codigo de resposta: " + responseCode);

//                    // Capturando os dados
//                    stream = connection.getInputStream();
//                    json = parseResponse(stream);

//                    Log.i("CEP", json);

                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        };

        asyncTask.execute((Void[]) null);
    }

}
