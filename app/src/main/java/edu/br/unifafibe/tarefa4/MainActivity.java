package edu.br.unifafibe.tarefa4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView notesListView;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Faz a requisição
        String url = "https://restcountries.eu/rest/v2/lang/pt";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            int responseLength = response.length();

                            for (int i = 0; i <= responseLength; i++){

                                arrayList.add(String.valueOf(response.getJSONObject(i).getString("name")));
                                latitude.add(response.getJSONObject(i).getJSONArray("latlng").getString(0).toString());
                                longitude.add(response.getJSONObject(i).getJSONArray("latlng").getString(1).toString());

                            }
                        }
                        catch (Exception ex){
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                System.err.println(error);
            }
        });

        MySingleton.getInstance(MainActivity.this).addToRequestQueue(request);

        //arrayList.add("CLIQUE EM ALGUM PAÍS");

        notesListView = (ListView) findViewById(R.id.lvLista);

        notesListView.setAdapter(new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                arrayList
        ));

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){

                }

                Intent it = new Intent(MainActivity.this, MapsActivity.class);
                it.putExtra("Nome", arrayList.get(position));
                it.putExtra("Lat", Double.parseDouble(latitude.get(position)));
                it.putExtra("Long", Double.parseDouble(longitude.get(position)));
                startActivity(it);

            }
        });


    }

}
