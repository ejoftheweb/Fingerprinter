package uk.co.platosys.fingerprinter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.platosys.effwords.EffwordLists;
import uk.co.platosys.fingerprinter.FPConstants;
import uk.co.platosys.fingerprinter.R;

public class TestPassphrase extends AppCompatActivity {
    @BindView(R.id.nameView)
    TextView nameView;
    @BindView(R.id.emailView)
    TextView emailView;
    @BindView(R.id.passPhraseBox)
    MultiAutoCompleteTextView multiAutoCompleteTextView;
    @BindView(R.id.passPhraseButton)
    Button passPhraseButton;
    char[] passPhrase;
    String name;
    String email;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_passphrase);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        this.passPhrase=intent.getCharArrayExtra(FPConstants.PASSPHRASE_INTENT_KEY);
        this.name= intent.getStringExtra(FPConstants.NAME_INTENT_KEY);
        this.email=intent.getStringExtra(FPConstants.EMAIL_INTENT_KEY);
        nameView.setText(name);
        emailView.setText(email);
        multiAutoCompleteTextView.setTokenizer(new SpaceTokenizer());
        multiAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, EffwordLists.getLongWordList()
                ));
        passPhraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveOn();
            }
        });
    }

    public void moveOn(){
        String stringPassphrase =multiAutoCompleteTextView.getText().toString();
        stringPassphrase = stringPassphrase.trim();
        char[] enteredPassphrase = stringPassphrase.toCharArray();

        if(Arrays.equals(enteredPassphrase,passPhrase)){
            Intent goOnIntent = new Intent (this, CreateProfile.class);
            goOnIntent.putExtra(FPConstants.PASSPHRASE_INTENT_KEY, passPhrase);
            goOnIntent.putExtra(FPConstants.EMAIL_INTENT_KEY, email);
            goOnIntent.putExtra(FPConstants.NAME_INTENT_KEY, name);
            startActivity(goOnIntent);
        }else{
            Intent goBackIntent = new Intent(this, LearnPassphrase.class);
            goBackIntent.putExtra(FPConstants.PASSPHRASE_INTENT_KEY, passPhrase);
            goBackIntent.putExtra(FPConstants.EMAIL_INTENT_KEY, email);
            goBackIntent.putExtra(FPConstants.NAME_INTENT_KEY, name);
            startActivity(goBackIntent);
        }
    }



    private class SpaceTokenizer implements MultiAutoCompleteTextView.Tokenizer {
        public int findTokenStart(CharSequence text, int cursor) {
            int i = cursor;
            while (i > 0 && text.charAt(i - 1) != ' ') {i--;}
            while (i < cursor && text.charAt(i) == ' ') {i++;}
            return i;
        }

        public int findTokenEnd(CharSequence text, int cursor) {
            int i = cursor;
            int len = text.length();
            while (i < len) {
                if (text.charAt(i) == ' ') {
                    return i;
                } else {
                    i++;
                }
            }
            return len;
        }

        public CharSequence terminateToken(CharSequence text) {
            int i = text.length();
            while (i > 0 && text.charAt(i - 1) == ' ') {i--;}
            if (i > 0 && text.charAt(i - 1) == ' ') {
                return text;
            } else {
                if (text instanceof Spanned) {
                    SpannableString sp = new SpannableString(text + " ");
                    TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                     Object.class, sp, 0);
                    return sp;
                } else {
                    return text + " ";
                }
            }
        }
    }
}
