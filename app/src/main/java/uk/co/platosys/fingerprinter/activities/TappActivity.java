package uk.co.platosys.fingerprinter.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.platosys.fingerprinter.R;
import uk.co.platosys.fingerprinter.activities.BaseActivity;

public class TappActivity extends BaseActivity {
    @BindView(R.id.imageSourceSpinner)
    Spinner imageSourceSpinner;

    @BindView(R.id.titleView)
TextView titleView;
@BindView(R.id.tweetView)
TextView tweetView;
@BindView(R.id.publisherNameView)
TextView publisherNameView;
@BindView(R.id.authorNameView)
TextView authorNameView;
@BindView(R.id.imageLayout)
LinearLayout imageLayout;
@BindView(R.id.contentView)
TextView contentView;
@BindView(R.id.addresseesButton)
Button addresseesButton;
@BindView(R.id.tagsButton)
Button tagsButton;
@BindView(R.id.endorsersButton)
Button endorsersButton;
@BindView(R.id.linksButton)
Button linksButton;
@BindView(R.id.fab)
FloatingActionButton fab;

ImageView illustrationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TA", "onCreate called");
        setContentView(R.layout.activity_tapp);
        ButterKnife.bind(this);
        contentView.setText("");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
