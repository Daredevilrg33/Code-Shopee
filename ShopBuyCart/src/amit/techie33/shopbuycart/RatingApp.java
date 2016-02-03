package amit.techie33.shopbuycart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RatingApp extends Activity {
	RatingBar ratebar;
	TextView name;
	RelativeLayout exit;
	Globals global;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rating);
		global = (Globals) getApplicationContext();
		ratebar = (RatingBar) findViewById(R.id.rate);
		name = (TextView) findViewById(R.id.name);

//		ratebar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
//
//			@Override
//			public void onRatingChanged(RatingBar ratingBar, float rating,
//					boolean fromUser) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(),
//						"Thanks For Rating"/* Float.toString(rating) */,
//						Toast.LENGTH_LONG).show();
//				finish();
//
//			}
//
//		});
		exit = (RelativeLayout) findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		name.setText(global.getusername());

	}
}
