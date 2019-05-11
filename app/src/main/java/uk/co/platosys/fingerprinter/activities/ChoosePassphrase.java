package uk.co.platosys.fingerprinter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.platosys.fingerprinter.FPConstants;
import uk.co.platosys.fingerprinter.R;
import uk.co.platosys.minigma.PassPhraser;

/**class provides an Activity which selects using */
public class ChoosePassphrase extends BaseActivity {
@BindView(R.id.learnPassphraseLabel)
    TextView learnPassphraseLabelView;
@BindView(R.id.passPhrase1)
TextView passphrase1View;
@BindView(R.id.passPhrase2)
TextView passphrase2View;
@BindView(R.id.passPhrase3)
TextView passphrase3View;
@BindView(R.id.passPhrase4)
TextView passPhrase4View;
@BindView(R.id.passPhrase5)
TextView passPhrase5View;
@BindView(R.id.passPhrase6)
TextView passPhrase6View;
@BindView(R.id.seekBar)
SeekBar seekBar;
ArrayList<TextView> wordlist = new ArrayList<>();

private Intent nextIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_passphrase);
        ButterKnife.bind(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                               @Override
                                               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                   if (fromUser){
                                                       if(progress>(FPConstants.MIN_PASSPHRASE_LENGTH-1)) {
                                                           updatePassPhrases(progress);
                                                       }else{
                                                           seekBar.setProgress(FPConstants.MIN_PASSPHRASE_LENGTH);
                                                           updatePassPhrases(FPConstants.MIN_PASSPHRASE_LENGTH);
                                                       }
                                                   }
                                               }

                                               @Override
                                               public void onStartTrackingTouch(SeekBar seekBar) {

                                               }

                                               @Override
                                               public void onStopTrackingTouch(SeekBar seekBar) {

                                               }
                                           });
                wordlist.add(passphrase1View);
        wordlist.add(passphrase2View);
        wordlist.add(passphrase3View);
        wordlist.add(passPhrase4View);
        wordlist.add(passPhrase5View);
        wordlist.add(passPhrase6View);
        for(TextView textView:wordlist){
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView1 = (TextView)view;
                    char[] passphrase = textView1.getText().toString().toCharArray();
                    confirmPassPhrase(passphrase);
                }
            });
        }


        Intent intent = getIntent();
        nextIntent= new Intent(this, LearnPassphrase.class);
       nextIntent.putExtra(FPConstants.NAME_INTENT_KEY,intent.getStringExtra(FPConstants.NAME_INTENT_KEY));
       //nextIntent.putExtra(FPConstants.EMAIL_INTENT_KEY,intent.getStringExtra(FPConstants.EMAIL_INTENT_KEY));
        updatePassPhrases(seekBar.getProgress());
    }

    private void updatePassPhrases(int length){
       for (TextView textView:wordlist){
           textView.setText(new String(PassPhraser.getPassPhrase(length)));
       }
    }
    private void confirmPassPhrase(char[] passPhrase){
       nextIntent.putExtra(FPConstants.PASSPHRASE_INTENT_KEY,passPhrase);
       startActivity(nextIntent);
    }

}
