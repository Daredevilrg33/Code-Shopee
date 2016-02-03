package amit.techie33.shopbuycart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint({ "HandlerLeak", "NewApi", "ViewHolder" })
public class Account extends Activity implements OnClickListener,
		AnimationListener {

	TextView appname, username, share, feedback, term, track, rate, logout,seller;
	Typeface font;
	LinearLayout header, logintab, usertab, outline;
	DisplayMetrics displaymetrics;
	int width, height;
	LinearLayout.LayoutParams latoutparams;
	String userid, selectedtab = "account", straddress;
	SharedPreferences autologin;
	Editor editor;
	Dialog dialog;
	double latitude, longitude;
	Animation animFadein;
	ImageView pdimageloader, HomeTab, CategoryTab, OfferTab, CartTab,
			AccountTab;
	Globals globals;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);

		autologin = getSharedPreferences("shopbuycart", 0);
		editor = autologin.edit();

		displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		height = displaymetrics.heightPixels;
		globals = (Globals) getApplicationContext();
		latoutparams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, (height / 3));
		font = Typeface.createFromAsset(getApplication().getAssets(),
				"Jacked_Font.ttf");
		seller = (TextView) findViewById(R.id.textView4_seller);
		track = (TextView) findViewById(R.id.track);
		term = (TextView) findViewById(R.id.term);
		share = (TextView) findViewById(R.id.share);
		header = (LinearLayout) findViewById(R.id.header);
		logintab = (LinearLayout) findViewById(R.id.logintab);
		usertab = (LinearLayout) findViewById(R.id.usertab);
		appname = (TextView) findViewById(R.id.appname);
		username = (TextView) findViewById(R.id.username);
		feedback = (TextView) findViewById(R.id.feedback);
		HomeTab = (ImageView) findViewById(R.id.home);
		CategoryTab = (ImageView) findViewById(R.id.category);
		OfferTab = (ImageView) findViewById(R.id.offers);
		CartTab = (ImageView) findViewById(R.id.cart);
		rate = (TextView) findViewById(R.id.rate);
		AccountTab = (ImageView) findViewById(R.id.account);
		logout = (TextView) findViewById(R.id.logout);
		outline = (LinearLayout) findViewById(R.id.outline);
		appname.setTypeface(font);
		HomeTab.setOnClickListener(this);
		CategoryTab.setOnClickListener(this);
		OfferTab.setOnClickListener(this);
		CartTab.setOnClickListener(this);
		AccountTab.setOnClickListener(this);
		logintab.setOnClickListener(this);
		share.setOnClickListener(this);
		term.setOnClickListener(this);
		feedback.setOnClickListener(this);
		track.setOnClickListener(this);
		logout.setOnClickListener(this);
		rate.setOnClickListener(this);
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		userid = globals.getuserid();
		if (userid != "0") {
			logintab.setVisibility(8);
			usertab.setVisibility(0);
			username.setText(globals.getusername().toUpperCase());
			logout.setVisibility(0);
			outline.setVisibility(0);
		}

		dialog = new Dialog(Account.this);
		
		seller.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent s=new Intent(getApplicationContext(),Seller_Login.class);
				startActivity(s);
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.logout:

			editor.putString("email", "");
			editor.putString("password", "");

			editor.commit();
			globals.setuserid("0");
			startActivity(new Intent(Account.this, Home.class));
			finish();
			overridePendingTransition(0, 0);

			break;

		
		case R.id.rate:

			if (userid != "0") {
				startActivity(new Intent(getApplicationContext(),
						RatingApp.class));

			} else {

				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);
				TextView message = (TextView) dialog.findViewById(R.id.message);

				message.setText("Please Login to Rate The Application");
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			}
			break;
		case R.id.term:
			startActivity(new Intent(getApplicationContext(), Terms.class));
			break;
		case R.id.logintab:
			dialog = new Dialog(Account.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.progressdialog);

			pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);

			pdimageloader.startAnimation(animFadein);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					dialog.dismiss();
					globals.setcatposition(0);
					startActivity(new Intent(getApplicationContext(),
							Login.class));

					overridePendingTransition(0, 0);
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;
		case R.id.track:
			globals.set_globalsearch("order");

			startActivity(new Intent(Account.this, Search.class));
			
			overridePendingTransition(0, 0);
			break;

		case R.id.home:
			dialog = new Dialog(Account.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.progressdialog);

			pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);

			pdimageloader.startAnimation(animFadein);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					dialog.dismiss();
					globals.setcatposition(0);
					startActivity(new Intent(getApplicationContext(),
							Home.class));

					overridePendingTransition(0, 0);
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;
		case R.id.feedback:

			final Intent emailIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			//
			emailIntent.setType("plain/text");
			emailIntent.putExtra(Intent.EXTRA_EMAIL,
					new String[] { "support@shopee.com" });
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SHOPEE");
			emailIntent.putExtra(Intent.EXTRA_TEXT, "www.shopee.com");

			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

			startActivity(Intent.createChooser(emailIntent, "Send mail..."));

			break;
		case R.id.share:

			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = "Here is the share content body";
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"APP SHARE ");
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent, "Share via"));

			break;
		case R.id.category:

			dialog = new Dialog(Account.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.progressdialog);

			pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);

			pdimageloader.startAnimation(animFadein);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					dialog.dismiss();
					globals.setcatposition(0);
					startActivity(new Intent(getApplicationContext(),
							Category.class));
					finish();
					overridePendingTransition(0, 0);
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;

		case R.id.cart:
			dialog = new Dialog(Account.this);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// setting custom layout to dialog
			dialog.setContentView(R.layout.progressdialog);

			pdimageloader = (ImageView) dialog.findViewById(R.id.pdimageloader);

			pdimageloader.startAnimation(animFadein);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					dialog.dismiss();
					globals.setcatposition(0);
					startActivity(new Intent(getApplicationContext(),
							Cart.class));

					overridePendingTransition(0, 0);
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);
			break;
		case R.id.account:
			selectedtab = "account";
			HomeTab.setImageResource(R.drawable.home);
			CategoryTab.setImageResource(R.drawable.list);
			OfferTab.setImageResource(R.drawable.offer);
			CartTab.setImageResource(R.drawable.cart);
			AccountTab.setImageResource(R.drawable.account_active);
			recreate();
			break;
		default:
			break;
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		if (animation == animFadein) {

		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}
}
