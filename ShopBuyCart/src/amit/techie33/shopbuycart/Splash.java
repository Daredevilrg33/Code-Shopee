package amit.techie33.shopbuycart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity implements AnimationListener {
	Animation animFadein;
	ImageView imageloader;
	String deviceid, devicetype, accesstoken;

	Dialog dialog;
	SharedPreferences autologin;
	Editor editor;
	Globals globals;

	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		autologin = getSharedPreferences("shopbuycart", 0);
		editor = autologin.edit();
		gps = new GPSTracker(getApplicationContext());
		globals = (Globals) getApplicationContext();
		dialog = new Dialog(getApplicationContext());
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		imageloader = (ImageView) findViewById(R.id.imageloader);

		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);

		// set animation listener
		animFadein.setAnimationListener(this);
		imageloader.startAnimation(animFadein);

		TelephonyManager mngr = (TelephonyManager) getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		deviceid = mngr.getDeviceId();
		devicetype = android.os.Build.MODEL + " " + android.os.Build.DISPLAY;
		accesstoken = mngr.getLine1Number();

		new Thread(null, registerdevice, "refresh_data").start();
	}

	Runnable registerdevice = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				result = WebServiceHandler.DeviceRegister(Splash.this,
						deviceid, devicetype, accesstoken);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			SignupHandler.sendMessage(msg);

		}
	};

	Handler SignupHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();
			if (res.equals("sucess")) {

				String email = autologin.getString("email", "");
				String password = autologin.getString("password", "");
				if (email != "" && password != "") {

					new Thread(null, LoginService, "refresh_data").start();

				} else {
					globals.setuserid("0");

					startActivity(new Intent(getApplicationContext(),
							Home.class));
					finish();
					overridePendingTransition(0, 0);
				}

			} else {

				// dialog = new Dialog(Splash.this);
				// dialog.getWindow().setBackgroundDrawable(
				// new ColorDrawable(android.graphics.Color.TRANSPARENT));
				// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// // setting custom layout to dialog
				// dialog.setContentView(R.layout.messagedailog);
				//
				// TextView message = (TextView)
				// dialog.findViewById(R.id.message);
				//
				// message.setText(res);
				// final TextView okbtn = (TextView) dialog
				// .findViewById(R.id.confirm);
				//
				// okbtn.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// globals.setuserid("0");
				// dialog.dismiss();
				startActivity(new Intent(getApplicationContext(), Home.class));
				finish();
				overridePendingTransition(0, 0);
				// }
				// });
				// dialog.show();

			}

		}
	};

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

	Runnable LoginService = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				String address = String.valueOf(gps.getLocation());

				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();
				String stremail = autologin.getString("email", "");
				String strpassword = autologin.getString("password", "");
				result = WebServiceHandler.UserLogin(getApplicationContext(),
						stremail, strpassword, address, latitude, longitude);

			} catch (Exception e) {
				e.printStackTrace();
				result = e.toString();
			}

			Message msg = new Message();
			msg.obj = result;
			LoginServiceHandler.sendMessage(msg);

		}
	};

	Handler LoginServiceHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {

						startActivity(new Intent(getApplicationContext(),
								Home.class));
						finish();
						overridePendingTransition(0, 0);

					}
				}, 10);

			} else if (res.equals("admin")) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {

						startActivity(new Intent(getApplicationContext(),
								AdminPage.class));
						finish();
						overridePendingTransition(0, 0);

					}
				}, 10);

			} else {
				if (dialog.isShowing())
					dialog.dismiss();
				dialog = new Dialog(Splash.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				final TextView okbtn = (TextView) dialog
						.findViewById(R.id.confirm);
				TextView message = (TextView) dialog.findViewById(R.id.message);

				message.setText(res);
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();

						editor.putString("email", "");
						editor.putString("password", "");

						editor.commit();

						globals.setuserid("0");

						startActivity(new Intent(getApplicationContext(),
								Home.class));
						finish();
						overridePendingTransition(0, 0);
					}
				});

				dialog.show();
			}
		}
	};
}
