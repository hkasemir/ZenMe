package name.heidik.zenme;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ZenActivity extends AppCompatActivity {
    private TextSwitcher tvQuote;
    private TextSwitcher tvAuthor;
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
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Typeface ebGaramond=Typeface.createFromAsset(getAssets(),"fonts/EBGaramond-Regular.ttf");
        setContentView(R.layout.activity_zen);
        tvAuthor = (TextSwitcher) findViewById(R.id.tvAuthor);
        tvQuote = (TextSwitcher) findViewById(R.id.tvQuote);

        tvQuote.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(ZenActivity.this);
                myText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                myText.setTypeface(ebGaramond);
                return myText;
            }
        });

        tvAuthor.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(ZenActivity.this);
                myText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                myText.setTypeface(ebGaramond);
                return myText;
            }
        });


        Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in_custom);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out_custom);

        tvQuote.setInAnimation(in);
        tvQuote.setOutAnimation(out);
        tvAuthor.setInAnimation(in);
        tvAuthor.setOutAnimation(out);


        tvAuthor.setText(quotes.get(quoteIndex).author);
        tvQuote.setText(quotes.get(quoteIndex).quote);

    }


    public void zenAgain(View v) {
        quoteIndex = (quoteIndex + 1) % quotes.size();
        tvQuote.setText(quotes.get(quoteIndex).quote);
        tvAuthor.setText(quotes.get(quoteIndex).author);
    }



}

