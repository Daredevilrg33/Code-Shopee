package amit.techie33.shopbuycart;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by om on 12/15/2015.
 */
public class Seller_Sign_up extends Activity 
{
    
    EditText user_name,mail,pass,confirm_pas;
    Button next;
    UsefullData objUsefullData;
    Boolean internet=false;
    RequestParams params = new RequestParams();
    GPSTracker gps;
    static Globals global;
    TextView login;
    SaveData save;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_sign_up);

        objUsefullData = new UsefullData(Seller_Sign_up.this);
         gps = new GPSTracker(getApplicationContext());
      
        user_name = (EditText) findViewById(R.id.user_name);
        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.pass);
        confirm_pas = (EditText) findViewById(R.id.confirm_pas);
        login = (TextView) findViewById(R.id.textView_login);
        save= new SaveData(Seller_Sign_up.this);

        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(getApplicationContext(), Seller_Login.class);
	               startActivity(intent);
				
			}
		});

        next=(Button) findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String use=user_name.getText().toString();
                String mails=mail.getText().toString();
                String user_pass=pass.getText().toString();
                String user_confirm_pas=confirm_pas.getText().toString();
                if (use.contains("")||mails.contains("")|user_pass.contains("")||user_confirm_pas.contains(""))
                {
                    

                    if(use.equals("")&&mails.equals("")&&user_pass.contains("")&&user_confirm_pas.contains(""))
                    {
                    	Toast.makeText(getApplicationContext(), "Blank Not allowed", Toast.LENGTH_SHORT).show();

                    }else {
                        
                            View view = Seller_Sign_up.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) Seller_Sign_up.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            
                            params.put("lat", String.valueOf(gps.getLatitude()));
                            params.put("lng", String.valueOf(gps.getLongitude()));
                            params.put("mail", mails);
                            params.put("uname", use);
                            params.put("pass", user_pass);
                            params.put("dtoken", "dhfjkdhkjfhsdkjfsdhfjdsfjkdshfjdshkjfhskjdf");
                            params.put("dtype", "0");
                            params.put("phone", user_confirm_pas);
                                
                                    Sign_up_process();

                        
                    }
                }

            }

            private void Sign_up_process()
            {
                AsyncHttpClient client = new AsyncHttpClient();
                client.post("http://jobsforyouonly.com/shopby/seller_sign_up.php", params,
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onStart() {
                                super.onStart();
                                objUsefullData.showProgress("Please wait..", "");
                            }

                            @Override
                            public void onFailure(Throwable error) {
                                objUsefullData.dismissProgress();
                                super.onFailure(error);
                            }

                            @Override
                            public void onFinish() {
                                objUsefullData.dismissProgress();

                                super.onFinish();
                            }

                            @Override
                            public void onSuccess(String response) {
                                try {
                                    Log.e("abc", "======response=======" + response);
                                    objUsefullData.dismissProgress();
                                    parse_sign_up(response);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            }
        });
    }

    private void parse_sign_up(String response)
    {


        Log.i(" Signup Response-", response);
        try {
            JSONObject object = new JSONObject(response);

            String status = object.getString("status");
            
           if (status.equals("successfull")) {
       		String Seller_ID = object.getJSONObject("result").getString("Seller_ID");
    		String Seller_Name = object.getJSONObject("result").getString("Seller_Name");
    		
    		save.save("seller_name", Seller_Name);
    		save.save("seller_id", Seller_ID);
			

               Intent intent = new Intent(getApplicationContext(), Seller_home.class);
               startActivity(intent);

            } else {

            	   Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
              	
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }

}
