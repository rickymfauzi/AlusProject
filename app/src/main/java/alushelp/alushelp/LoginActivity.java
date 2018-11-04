package alushelp.alushelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Kode Utama
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        inputEmail = findViewById(R.id.emailUser);
        inputPassword = findViewById(R.id.passwordUser);
        buttonLogin = findViewById(R.id.btnLogin);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String emailUser = inputEmail.getText().toString();
                final String passwordUser = inputPassword.getText().toString();

                if (TextUtils.isEmpty(emailUser)) {
                    Toast.makeText(getApplicationContext(), "Masukkan Email / No.HP", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordUser)) {
                    Toast.makeText(getApplicationContext(), "Masukkan Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate User
                auth.signInWithEmailAndPassword(emailUser, passwordUser)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (passwordUser.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
        // Akhir Kode Utama

    }

    public void onBackPressed() {
        moveTaskToBack(true);
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(1);

    }

    public void BantuLupaPassword(View view) {
    }

    public void Daftar(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
