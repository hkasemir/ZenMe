package name.heidik.zenme;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ZenActivity extends ActionBarActivity {
    private TextView tvQuote;
    private TextView tvAuthor;
    private List<Quote> quotes;
    private int quoteIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            String json = null;
            InputStream is = getAssets().open("quotes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<ArrayList<Quote>> tr = new TypeReference<ArrayList<Quote>>() {};
            quotes = mapper.readValue(json, tr);
            Log.d("DEBUG", "quotes: " + quotes.toString());
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_zen);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvQuote = (TextView) findViewById(R.id.tvQuote);

        tvAuthor.setText(quotes.get(quoteIndex).author);
        tvQuote.setText(quotes.get(quoteIndex).quote);

    }

    public void zenAgain(View v) {
        quoteIndex = (quoteIndex + 1) % quotes.size();
        tvAuthor.setText(quotes.get(quoteIndex).author);
        tvQuote.setText(quotes.get(quoteIndex).quote);
    }



}

