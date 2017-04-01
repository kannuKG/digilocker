package in.ac.cusat.smartindia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends AppCompatActivity {
    EditText name,email,pass,phoneno;
    Button signup;
    FirebaseAuth auth;
    ProgressDialog dialog;
    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference mProfileRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_up);
        name=(EditText) findViewById(R.id.Name);
        phoneno=(EditText) findViewById(R.id.Phoneno);
        email =(EditText)findViewById(R.id.email);
        pass =(EditText)findViewById(R.id.pass);
        signup =(Button)findViewById(R.id.signup);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                auth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    //Log.e("hello","akshay");
                                    //FirebaseUser user = task.getResult().getUser();
                                    mProfileRef=mRootRef.child(""+task.getResult().getUser().getUid());
                                    User u=new User();
                                    u.setName("ankit");
                                    u.setPhoneno("828282828");
                                    mProfileRef.setValue(u);
                                    dialog.dismiss();
                                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                }
                            }
                        });
            }
        });
    }
}
