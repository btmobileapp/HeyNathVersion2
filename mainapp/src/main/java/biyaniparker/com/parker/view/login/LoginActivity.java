package biyaniparker.com.parker.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.File;
import biyaniparker.com.parker.R;
import biyaniparker.com.parker.bal.ModuleLogin;
import biyaniparker.com.parker.fcm.FcmUtility;
import biyaniparker.com.parker.utilities.CommonUtilities;
import biyaniparker.com.parker.utilities.DownloadUtility;
import biyaniparker.com.parker.utilities.serverutilities.ConnectionDetector;
import biyaniparker.com.parker.view.user.UserCreateView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  ,DownloadUtility
{
//TextInputLayout
    Button btnLogin,btnRegister;
    EditText edUserId,edPassword;
    ModuleLogin objModuleLogin;
    TextView txtAddress,txt,txtemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String appname=getString(R.string.app_name);

        if(appname.equalsIgnoreCase("Choice Electric"))
        {
            setContentView(R.layout.activity_login_choice);
        }
        else if(appname.equalsIgnoreCase("Rajashree Industries"))
        {
            setContentView(R.layout.content_login_rajashri2);
        }
        else
        {
            setContentView(R.layout.activity_login_rajashri);
        }
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
      //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // getSupportActionBar().hide();
        objModuleLogin=new ModuleLogin(this);
        inItUi();
     //   toolbar.setVisibility(View.GONE);

        CommonUtilities.hideatInItInputBoard(this);



        File myDir = new File(Environment.getExternalStorageDirectory().toString()+"/parker" );
        myDir.mkdirs();


        txtAddress=(TextView)findViewById(R.id.txtAddress);
        txt=(TextView)findViewById(R.id.txt);
        txtemail = findViewById(R.id.txtemail);
        txtAddress.setText(CommonUtilities.AdminAdress);
        txt.setText(CommonUtilities.AdminContact);
        //txtemail.setText(CommonUtilities.AdminEmail);


        try
        {
            FcmUtility fcmUtility = new FcmUtility();
            fcmUtility.callProcedure(this);
        }
        catch (Exception ex){
            CommonUtilities.alert(this,ex.toString());
        }
        try {
            new FcmUtility().callProcedure(this);
        }
        catch (Exception ex){}
    }

    private void inItUi()
    {
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edUserId=(EditText)findViewById(R.id.edUserId);
        edPassword=(EditText)findViewById(R.id.edPassword);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        CommonUtilities.hideatInItInputBoard(this);
        if (v.getId()==R.id.btnLogin) {
            CommonUtilities.hideSoftKeyBord(this);
            String userid = edUserId.getText().toString();
            String pass = edPassword.getText().toString();
            if (userid.trim().equalsIgnoreCase("") || pass.trim().equalsIgnoreCase("")) {
                Toast.makeText(this, "Please enter username and password ", Toast.LENGTH_LONG).show();
            } else {
                if (new ConnectionDetector(this).isConnectingToInternet()) {
                    objModuleLogin.performLogin(userid.trim(), pass.trim());
                } else {
                    Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        }
        else if (v.getId()==R.id.btnRegister){
               finish();
              Intent intent = new Intent(LoginActivity.this, UserCreateView.class);
              intent.putExtra("IsRegistration",true);
              startActivity(intent);
        }
    }


    @Override
    public void onComplete(String str, int requestCode,int responseCode)
    {
        if(responseCode==200) {
            if (requestCode == 1)
            {
                if (str.equalsIgnoreCase("LoginSuceess"))
                {
                    finish();
                    //if(UserUtilities.getUserType(this).equalsIgnoreCase("Admin"))
                    if(true)
                    {
                        SharedPreferences sh=getSharedPreferences("Sync",MODE_PRIVATE);
                        sh.edit().putBoolean("Sync",false).commit();

                        startActivity(new Intent(this, SyncActivity.class));
                        overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                    }
                    else
                    {

                    }
                }
                else if (str.equalsIgnoreCase("LoginNotApproved"))
                {
                  //  Toast.makeText(this, "Incorrect UserName or Password ", Toast.LENGTH_LONG).show();
                    CommonUtilities.alert(this,"User not approved yet, Please contact to Rajashree Industries.");
                }
                else if (str.equalsIgnoreCase("LoginFailed"))
                {
                    Toast.makeText(this, "Incorrect UserName or Password ", Toast.LENGTH_LONG).show();
                }
                else if (str.equalsIgnoreCase("AllreadyLogin"))
                {
                    //  Toast.makeText(this, "Incorrect UserName or Password ", Toast.LENGTH_LONG).show();
                    CommonUtilities.alert(this,"User has allready login on other device, Please contact to Rajashree Industries.");
                }

            }
        }
        else
        {
            Toast.makeText(this, "Server Communication Failed", Toast.LENGTH_LONG).show();
        }

    }
}
