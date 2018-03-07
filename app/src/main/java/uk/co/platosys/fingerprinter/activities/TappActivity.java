package uk.co.platosys.fingerprinter.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.platosys.fingerprinter.R;
import uk.co.platosys.fingerprinter.activities.BaseActivity;

/**This is the base class for Vouch activities
 * having the basic Vouchr layout.
 *
 */
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
@BindView(R.id.illustrationView)
ImageView illustrationView;

int illustrationWidth;
int illustrationHeight;
static final float illustrationWidthRatio= 0.85f;
static final float illustrationHeightRatio=0.3f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TA", "onCreate called");
        setContentView(R.layout.activity_tapp);
        ButterKnife.bind(this);
        illustrationWidth= (int) (screenWidth*illustrationWidthRatio);
        illustrationHeight= (int) (screenHeight*illustrationHeightRatio);
        illustrationView.setMaxWidth(illustrationWidth);
        illustrationView.setMinimumWidth(illustrationWidth);
        illustrationView.setMaxHeight(illustrationHeight);
        illustrationView.setMinimumHeight(illustrationHeight);

    }

}
