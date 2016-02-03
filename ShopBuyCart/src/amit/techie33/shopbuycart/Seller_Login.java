package amit.techie33.shopbuycart;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
public class Seller_Login extends Activity 

{

    
    TextView register,forgot_paaword,forgot_id;
    Button Seller_Login_btn;
    EditText user_name,user_password;
    static int forgot_request=0;
    
    
    RequestParams params = new RequestParams();
    GPSTracker gps;
    SaveData save;
    UsefullData objUsefullData;
    static String use,pas;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.seller_login);



        save= new SaveData(Seller_Login.this);
         objUsefullData = new UsefullData(Seller_Login.this);
        gps = new GPSTracker(getApplicationContext());
        user_name = (EditText) findViewById(R.id.editusername);
        user_password = (EditText) findViewById(R.id.editpassword);
        forgot_paaword = (TextView) findViewById(R.id.forgot_paaword);
        forgot_id = (TextView) findViewById(R.id.forgot_id);
   

        register=(TextView) findViewById(R.id.Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reg = new Intent(getApplicationContext(), Seller_Sign_up.class);
                startActivity(reg);



            }
        });

        Seller_Login_btn=(Button) findViewById(R.id.button_login);
        Seller_Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                use = user_name.getText().toString();
                pas = user_password.getText().toString();
                if (use.contains("") || pas.contains("")) {
              

                    if (use.equals("") && pas.equals("")) {
                        
                        

                      	Toast.makeText(getApplicationContext(), "Please Enter User Name And Password", Toast.LENGTH_SHORT).show();

                    }  else {
                           View view = Seller_Login.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) Seller_Login.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        
                                 params.put("uname", use);
                            params.put("pass", pas);

                            Seller_Login_process();

                        


                    }
                }

            }
        });


    }

    public void Seller_Login_process() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://jobsforyouonly.com/shopby/seller_login.php", params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        objUsefullData.showProgress("Please wait a moment", "");
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
                            parse_Seller_Login(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public void parse_Seller_Login(String response) {


        Log.i(" Seller_Login Response-", response);

           
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
