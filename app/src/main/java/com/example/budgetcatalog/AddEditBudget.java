package com.example.budgetcatalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditBudget extends AppCompatActivity {

    DbHelper db;
    EditText edName=null, edDescription=null, edData=null;
    Button btnSave;
    String rowId, mode, name, description, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_gas_station);

        db = new DbHelper(this);

        edName = (EditText) findViewById(R.id.edName);
        edData = (EditText) findViewById(R.id.edData);
        edDescription = (EditText) findViewById(R.id.edDescription);
        btnSave = (Button) findViewById(R.id.btnSave);

        Intent intent = this.getIntent();
        this.mode = intent.getStringExtra("mode");

        if (!mode.equals("add")) {
            this.rowId = intent.getStringExtra("rowId");

            this.name = intent.getStringExtra("name");
            this.description = intent.getStringExtra("description");
            this.data = intent.getStringExtra("data");

            edName.setText(name);
            edDescription.setText(description);
            edData.setText(data);
        }
    }

    //обработчик кнопки добавления
    public void onClickBtn(View v) {

        if (v.getId()!=R.id.btnSave) return;

        String query = null, message;

        if (mode.equals("add")) {
            query = "insert into План(наименование,описание,дата) values('" + edName.getText() + "','" +
                    edDescription.getText() + "','" + edData.getText() + "');";
            message="Не возможно добавить данные в БД, повторите попытку!";
        }
        else {
            query = "update Автозаправка set наименование='" + edName.getText() + "', описание='" +
                    edDescription.getText() + "', дата='" + edData.getText() + "' where _id=" + rowId + ";";
            message="Не возможно обновить данные в БД, повторите попытку!";
        }

        if (!db.sqlExec(query)) {
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}