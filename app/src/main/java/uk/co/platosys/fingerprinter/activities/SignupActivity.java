package uk.co.platosys.fingerprinter.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.platosys.fingerprinter.FPConstants;
import uk.co.platosys.fingerprinter.R;
import uk.co.platosys.minigma.exceptions.Exceptions;

public class SignupActivity extends BaseActivity {

   @BindView(R.id.login_button)
    TwitterLoginButton twitterLoginButton;
    @BindView(R.id.getTwitterButton)
    Button getTwitterButton;
    String name;
    TwitterSession twitterSession;
    boolean sessionSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
       reportBinding();
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                twitterSession = result.data;
                if(bound){
                    vouchService.setTwitterSession(twitterSession);
                    sessionSet=true;
                }
                name=twitterSession.getUserName();
                selectPassphrase(name, twitterSession);
            }

            @Override
            public void failure(TwitterException exception) {
                Exceptions.dump(exception);
            }
        });

        getTwitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(FPConstants.PLAYSTORE_TWITTER_URL));
                startActivity(intent);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void selectPassphrase(String name, TwitterSession twitterSession){
        reportBinding();
        if(!sessionSet){
            if(bound){
                vouchService.setTwitterSession(twitterSession);
                sessionSet=true;
            }else{
                Log.d("SA", "not yet bound, session not yet set");
            }
        }
        Intent selectPassphraseIntent = new Intent(this, ChoosePassphrase.class);

        selectPassphraseIntent.putExtra("name",name);
        //intent.putExtra( "email", email);
        startActivity(selectPassphraseIntent);

    }



}
