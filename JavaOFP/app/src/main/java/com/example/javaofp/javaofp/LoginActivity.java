package com.example.javaofp.javaofp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javaofp.javaofp.dominio.Estudante;
import com.example.javaofp.javaofp.dominio.Perfil;
import com.example.javaofp.javaofp.seguranca.Criptografia;
import com.example.javaofp.javaofp.serviceWS.RetrofitConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Estudante estudante = new Estudante();
    private EditText etNickname, etSenha;
    private Button btLogin;
    private ImageView ivPerfil;
    private CheckBox cbSaveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNickname = findViewById(R.id.etNickname);
        etSenha = findViewById(R.id.etSenha);
        btLogin = findViewById(R.id.btLogin);
        ivPerfil = findViewById(R.id.ivPerfil);
        cbSaveLogin = findViewById(R.id.cbSaveLogin);
        TextView tvRegistrar = findViewById(R.id.tvRegistrar);

        loadSharedPreferences();

        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onclickLogin(View view) {
        if(!etNickname.getText().toString().isEmpty()){
            if(!etSenha.getText().toString().isEmpty()){
                cbSaveLogin.setActivated(false);

                btLogin.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                btLogin.setActivated(false);

                try{
                    solicitarLogin(etNickname.getText().toString(), Criptografia.criptografar(etSenha.getText().toString(), "MD5"));

                }catch (Exception ex){
                    ex.getStackTrace();
                }
            }else{
                etSenha.setError("Campo de Senha obrigatório");
                etSenha.setFocusable(true);
            }
        }else{
            etNickname.setError("Campo de Nickname obrigatório");
            etNickname.setFocusable(true);
        }
    }

    public void solicitarLogin(final String nickname, final String senha){
        Call <Estudante> call = new RetrofitConfig().getEstudanteService().solicitarLogin(nickname, senha);

        call.enqueue(new Callback<Estudante>() {
            @Override
            public void onResponse(Call<Estudante> call, Response<Estudante> response) {
                estudante = response.body();

                if(estudante == null){
                    Toast.makeText(getApplicationContext(),"Nickname e/ou Senha inválidos",Toast.LENGTH_LONG).show();
                    etNickname.setText("");
                    etSenha.setText("");
                }else{
                    if(cbSaveLogin.isChecked()){
                        saveSharedPrefences();
                    }

                    ivPerfil.setImageResource(Perfil.getPerfil(estudante.getFoto()));

                    Toast.makeText(getApplicationContext(),"Seja bem-vindo ao JavaOFP",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("estudante", estudante);
                    startActivity(intent);

                    finish();
                }
            }

            @Override
            public void onFailure(Call<Estudante> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Verifique sua conexão",Toast.LENGTH_LONG).show();
            }
        });

        btLogin.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        btLogin.setActivated(true);
    }

    public void saveSharedPrefences(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("loadConta", cbSaveLogin.isChecked());
        editor.putString("nickname", etNickname.getText().toString());
        editor.putString("senha", etSenha.getText().toString());
        editor.apply();
    }

    public void loadSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);

        if(sharedPreferences.getBoolean("loadConta", false)){
            solicitarLogin(sharedPreferences.getString("nickname", null), sharedPreferences.getString("senha", null));
        }
    }
}
