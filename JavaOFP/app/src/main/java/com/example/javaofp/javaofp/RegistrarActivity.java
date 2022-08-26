package com.example.javaofp.javaofp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javaofp.javaofp.dominio.Estudante;
import com.example.javaofp.javaofp.seguranca.Criptografia;
import com.example.javaofp.javaofp.serviceWS.RetrofitConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarActivity extends AppCompatActivity {
    private EditText etNickname, etEmail, etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        etNickname = findViewById(R.id.etNickname);
        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
    }

    public void onclickRegistro(View view){
        if(!etNickname.getText().toString().isEmpty()){
            if(!etEmail.getText().toString().isEmpty()){
                if(!etSenha.getText().toString().isEmpty()){
                    if(etSenha.getText().toString().length() >= 6){
                        try{
                            solicitarRegistro(new Estudante(etNickname.getText().toString(), Criptografia.criptografar(etSenha.getText().toString(), "MD5"),etEmail.getText().toString(),3));

                        }catch (Exception ex){
                            ex.getStackTrace();
                        }
                    }else{
                        etSenha.setError("Senha muito curtas no mínimo 6 caracteres");
                        etSenha.setFocusable(true);
                    }
                }else{
                    etSenha.setError("Campo de Senha obrigatório");
                    etSenha.setFocusable(true);
                }
            }else{
                etEmail.setError("Campo de E-mail obrigatório");
                etEmail.setFocusable(true);
            }
        }else{
            etNickname.setError("Campo de Nickname obrigatório");
            etNickname.setFocusable(true);
        }
    }

    public void solicitarRegistro(final Estudante estudante){
        Call<Boolean> call = new RetrofitConfig().getEstudanteService().solicitarRegistro(estudante);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(getApplicationContext(),"Cadastro efetuado com sucesso",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegistrarActivity.this, MainActivity.class);
                    intent.putExtra("estudante", estudante);
                    startActivity(intent);

                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Dados inválidos",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Verifique sua conexão",Toast.LENGTH_LONG).show();
            }
        });
    }

}
