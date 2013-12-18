package com.cwat.nades;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by alexthornburg on 12/8/13.
 */
public class JoinOrSignUp extends Activity {

    Button createAccount;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        createAccount = (Button) findViewById(R.id.create_account);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinOrSignUp.this,Login.class);
                startActivity(intent);
            }
        });
        createAccount.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(JoinOrSignUp.this,CreateAccount.class);
                startActivity(intent);

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_screen, menu);
        return true;
    }

}
