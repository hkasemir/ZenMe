package name.heidik.zenme;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class AlternateActivity extends Activity {
    private List<Quote> quotes;
    private int mQuoteIndex = 0;
    private TextView mtvAuthor;
    private TextView mtvQuote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternate);

        readJson();
        initView();
    }

    public void readJson() {

        try {
            String json = null;
            InputStream is = getAssets().open("quotes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<ArrayList<Quote>> tr = new TypeReference<ArrayList<Quote>>() {
            };
            quotes = mapper.readValue(json, tr);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initView(){

        final Typeface ebGaramond=Typeface.createFromAsset(getAssets(),"fonts/EBGaramond-Regular.ttf");
        setContentView(R.layout.activity_alternate);

        mtvAuthor = (TextView) findViewById(R.id.tvAuthor);
        mtvQuote = (TextView) findViewById(R.id.tvQuote);

        mtvAuthor.setTypeface(ebGaramond);
        mtvQuote.setTypeface(ebGaramond);

        updateText();


    }

    public void updateText(){



        final Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in_custom);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out_custom);

        final LinearLayout quoteLayout = (LinearLayout) findViewById(R.id.quote_layout);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mtvAuthor.setText(quotes.get(mQuoteIndex).author);
                mtvQuote.setText(quotes.get(mQuoteIndex).quote);
                quoteLayout.startAnimation(in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        quoteLayout.startAnimation(out);


    }

    public void zenAgain(View v) {
        mQuoteIndex = (mQuoteIndex + 1) % quotes.size();
        updateText();
    }
}


