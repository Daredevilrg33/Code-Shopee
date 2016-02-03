package amit.techie33.shopbuycart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Seller_home extends Activity
{
	
	TextView user;
	SaveData s;
	Button add;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.seller_home);
	        s=new SaveData(getApplicationContext());
	        
	        user=(TextView) findViewById(R.id.textView1_user);
	        user.setText("Welcome ... "+s.getString("seller_name"));
	        add=(Button) findViewById(R.id.addproduct);
	        add.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent add=new Intent(getApplicationContext(),Seller_add_product.class);
					startActivity(add);
				}
			});



	    }

}
