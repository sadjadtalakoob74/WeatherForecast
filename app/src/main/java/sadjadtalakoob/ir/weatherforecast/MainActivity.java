package sadjadtalakoob.ir.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    ListView listView;
    Button button;

    String cityname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.txt_view);
        editText = findViewById(R.id.edt_text);
        listView = findViewById(R.id.list_view);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cityname = editText.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://www.metaweather.com/api/location/search/?query=" + cityname;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                //textView.setText("Response is: " + response.substring(0, 500));
                                List list = new ArrayList();

                                Toast.makeText(MainActivity.this, "Response is:" + response.toString(), Toast.LENGTH_LONG).show();
                                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, Collections.singletonList(response.toString()));
                                listView.setAdapter(arrayAdapter);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "No Connection/Communication Error!", Toast.LENGTH_SHORT).show();

                        } else if (volleyError instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "Authentication/ Auth Error!", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                queue.add(stringRequest);
            }
        });


    }
}