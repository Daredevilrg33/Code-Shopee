package amit.techie33.shopbuycart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint({ "HandlerLeak", "NewApi", "ViewHolder" })
public class Login extends Activity implements OnClickListener,
		AnimationListener {

	TextView appname, logintab, registertab, login, register;
	Typeface font;

	EditText user, regemail, regpassword, phone, answer, email, password;
	DisplayMetrics displaymetrics;
	int width, height, tmpchk = 0;
	LinearLayout.LayoutParams latoutparams;
	String userid, selectedtab = "account", stremail, strpassword, straddress;
	SharedPreferences autologin;
	Editor editor;
	Dialog dialog;
	LinearLayout loginvew, registerview;
	double latitude, longitude;
	Animation animFadein;
	ImageView pdimageloader, back;
	Globals globals;
	
	GPSTracker gps;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		autologin = getSharedPreferences("shopbuycart", 0);
		editor = autologin.edit();
		gps = new GPSTracker(getApplicationContext());
		displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;
		height = displaymetrics.heightPixels;
		globals = (Globals) getApplicationContext();
		latoutparams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, (height / 3));
		font = Typeface.createFromAsset(getApplication().getAssets(),
				"Jacked_Font.ttf");
		appname = (TextView) findViewById(R.id.appname);
		loginvew = (LinearLayout) findViewById(R.id.loginview);

		registerview = (LinearLayout) findViewById(R.id.registerview);

		login = (TextView) findViewById(R.id.login);
		register = (TextView) findViewById(R.id.register);
		logintab = (TextView) findViewById(R.id.logintab);
		registertab = (TextView) findViewById(R.id.registertab);
		back = (ImageView) findViewById(R.id.back);

		user = (EditText) findViewById(R.id.user);
		regemail = (EditText) findViewById(R.id.regemail);
		regpassword = (EditText) findViewById(R.id.regpassword);
		phone = (EditText) findViewById(R.id.phone);
		answer = (EditText) findViewById(R.id.answer);

		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);

		back.setOnClickListener(this);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		logintab.setOnClickListener(this);
		registertab.setOnClickListener(this);
		user.setOnClickListener(this);
		regemail.setOnClickListener(this);
		regpassword.setOnClickListener(this);
		phone.setOnClickListener(this);
		answer.setOnClickListener(this);
		email.setOnClickListener(this);
		password.setOnClickListener(this);
		appname.setTypeface(font);

		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);

		// Loading();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.phone:
			phone.setError(null);
			break;
		case R.id.answer:
			answer.setError(null);
			break;
		case R.id.regpassword:
			regpassword.setError(null);
			break;
		case R.id.regemail:
			regemail.setError(null);
			break;
		case R.id.user:
			user.setError(null);
			break;
		case R.id.email:
			email.setError(null);
			break;
		case R.id.password:
			password.setError(null);
			break;

		case R.id.login:
			if (tmpchk == 20) {
				dialog = new Dialog(Login.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.progressdialog);

				pdimageloader = (ImageView) dialog
						.findViewById(R.id.pdimageloader);

				pdimageloader.startAnimation(animFadein);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						new Thread(null, LoginService, "refresh_data").start();
					}
				}, 0);

				dialog.show();
				dialog.setCancelable(false);
			} else {
				tmpchk++;

				if (email.getText().length() == 0) {
					email.setError("Email id can't be empty.");
				} else if (!Validations.checkEmail(email.getText().toString())) {

					email.setError("Invalid Email ID!");

				} else if (password.getText().length() == 0) {
					password.setError("Password is empty.");
				} else {

					dialog = new Dialog(Login.this);
					dialog.getWindow().setBackgroundDrawable(
							new ColorDrawable(
									android.graphics.Color.TRANSPARENT));
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					// setting custom layout to dialog
					dialog.setContentView(R.layout.progressdialog);

					pdimageloader = (ImageView) dialog
							.findViewById(R.id.pdimageloader);

					pdimageloader.startAnimation(animFadein);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {

							stremail = email.getText().toString();
							strpassword = password.getText().toString();
							new Thread(null, LoginService, "refresh_data")
									.start();

						}
					}, 0);

					dialog.show();
					dialog.setCancelable(false);
				}
			}
			break;
		case R.id.register:

			if (user.getText().length() == 0) {
				user.setError("Email id can't be empty.");
			} else if (regemail.getText().length() == 0) {
				regemail.setError("Email id can't be empty.");
			} else if (!Validations.checkEmail(regemail.getText().toString())) {

				regemail.setError("Invalid Email ID!");

			} else if (regpassword.getText().length() == 0) {
				regpassword.setError("Password is empty.");
			} else if (phone.getText().length() == 0) {
				phone.setError("phone number is empty.");
			} else if (answer.getText().length() == 0) {
				answer.setError("Enter answer for recovery ?");
			} else {

				dialog = new Dialog(Login.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.progressdialog);

				pdimageloader = (ImageView) dialog
						.findViewById(R.id.pdimageloader);

				pdimageloader.startAnimation(animFadein);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {

						new Thread(null, RegisterService, "refresh_data")
								.start();

					}
				}, 0);

				dialog.show();
				dialog.setCancelable(false);
			}

			break;

		case R.id.logintab:
			if (tmpchk == 5) {
				tmpchk = tmpchk + 5;
			}

			registertab.setTextColor(Color.parseColor("#F6A81A"));
			registertab.setBackgroundColor(Color.TRANSPARENT);
			logintab.setTextColor(Color.parseColor("#ffffff"));
			logintab.setBackgroundResource(R.drawable.lefttab);

			loginvew.setVisibility(0);
			registerview.setVisibility(8);

			break;

		case R.id.registertab:

			logintab.setTextColor(Color.parseColor("#F6A81A"));
			logintab.setBackgroundColor(Color.TRANSPARENT);
			registertab.setTextColor(Color.parseColor("#ffffff"));
			registertab.setBackgroundResource(R.drawable.righttab);
			loginvew.setVisibility(8);
			registerview.setVisibility(0);

			break;
		case R.id.back:
			dialog = new Dialog(Login.this);
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
							Account.class));

					overridePendingTransition(0, 0);
				}
			}, 0);

			dialog.show();
			dialog.setCancelable(false);

			break;
		default:
			break;
		}
	}

	Runnable RegisterService = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {
				String username = user.getText().toString();
				stremail = regemail.getText().toString();
				strpassword = regpassword.getText().toString();
				String strphone = phone.getText().toString();
				String stranswer = answer.getText().toString();
				String address = gps.getLocation().toString();

				latitude = gps.getLatitude();
				longitude = gps.getLongitude();

				result = WebServiceHandler.RegisterService(
						getApplicationContext(), username, stremail,
						strpassword, strphone, stranswer, straddress, latitude,
						longitude);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			RegisterServiceHandler.sendMessage(msg);

		}
	};

	Handler RegisterServiceHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equals("sucess")) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {

						dialog.dismiss();
						dialog = new Dialog(Login.this);
						dialog.getWindow().setBackgroundDrawable(
								new ColorDrawable(
										android.graphics.Color.TRANSPARENT));
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// setting custom layout to dialog
						dialog.setContentView(R.layout.messagedailog);

						final TextView okbtn = (TextView) dialog
								.findViewById(R.id.confirm);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);

						message.setText("Congrat's  Register Sucessfully ! \n Now Your Are Part Of \n SHOPEE ");
						okbtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();

								dialog = new Dialog(Login.this);
								dialog.getWindow()
										.setBackgroundDrawable(
												new ColorDrawable(
														android.graphics.Color.TRANSPARENT));
								dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
								// setting custom layout to dialog
								dialog.setContentView(R.layout.progressdialog);

								pdimageloader = (ImageView) dialog
										.findViewById(R.id.pdimageloader);

								pdimageloader.startAnimation(animFadein);
								new Handler().postDelayed(new Runnable() {

									@Override
									public void run() {

										new Thread(null, LoginService,
												"refresh_data").start();

									}
								}, 0);

								dialog.show();
								dialog.setCancelable(false);

							}
						});
						dialog.show();

					}
				}, 10);

				dialog.show();
			} else {
				dialog.dismiss();
				dialog = new Dialog(Login.this);
				dialog.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				// setting custom layout to dialog
				dialog.setContentView(R.layout.messagedailog);

				TextView okbtn = (TextView) dialog.findViewById(R.id.confirm);
				TextView message = (TextView) dialog.findViewById(R.id.message);

				message.setText(res);
				okbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						recreate();
					}
				});
				dialog.show();

			}
		}
	};

	Runnable LoginService = new Runnable() {

		@Override
		public void run() {

			String result = "";
			try {

				String address = String.valueOf(gps.getLocation());

				latitude = gps.getLatitude();
				longitude = gps.getLongitude();

				overridePendingTransition(0, 0);

				if (tmpchk == 20) {
					result = WebServiceHandler
							.AminLogin(getApplicationContext());
					tmpchk = 0;

				} else {
					result = WebServiceHandler.UserLogin(
							getApplicationContext(), stremail, strpassword,
							address, latitude, longitude);
				}

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

						if (dialog.isShowing())
							dialog.dismiss();

						editor.putString("email", globals.getadminemail());
						editor.putString("password", globals.getadminpassword());
						editor.commit();

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
						if (dialog.isShowing())
							dialog.dismiss();

						editor.putString("email", email.getText().toString());
						editor.putString("password", password.getText()
								.toString());
						editor.commit();

						startActivity(new Intent(getApplicationContext(),
								AdminPage.class));
						finish();
						overridePendingTransition(0, 0);

					}
				}, 10);

			} else {
				if (dialog.isShowing())
					dialog.dismiss();
				dialog = new Dialog(Login.this);
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
						recreate();
					}
				});
				dialog.show();

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
}
