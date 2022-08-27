package zombi.kampung.pisang22.firestoreuitm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etTitle, etDesc;
    Button btnSave, btnShow;
    String titleStr, descStr, idStr;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);

        db = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(this);
        btnShow.setOnClickListener(this);

        // get data from intent
        Bundle container = getIntent().getExtras();

        if(container != null) {
            // tukar text on save button jdi update
            btnSave.setText("update");

            // get the data from container
            titleStr = container.getString("key_title");
            descStr = container.getString("key_desc");
            idStr = container.getString("key_id");

            // display title and desc in input text
            etTitle.setText(titleStr);
            etDesc.setText(descStr);
        } else {
            btnSave.setText("save");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:

                String titleStr = etTitle.getText().toString().trim(),
                        descStr = etDesc.getText().toString().trim();

                // check if there is any bundle
                Bundle container = getIntent().getExtras();
                if(container != null) {
                    String id = idStr;
                    updateData(id, titleStr, descStr);
                } else {
                    String id = UUID.randomUUID().toString();
                    // call method savedata()
                    saveData(id, titleStr, descStr);
                }
                break;
            case R.id.btnShow:

                Intent i = new Intent(this, ShowActivity.class);
                startActivity(i);
                break;
        }
    }

    public void saveData(String idStr, String titleStr, String descStr) {
        //check data not empty
        if(!titleStr.isEmpty() && !descStr.isEmpty()) {
            // data will be store in firestore as key-value pairing
            HashMap<String, Object> map = new HashMap<>();

            //put data into map
            map.put("key_id", idStr);
            map.put("key_title", titleStr);
            map.put("key_desc", descStr);


            // execute insert data
            db.collection("Documents").document(idStr).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this,
                                        "Data inserted successfully",
                                        Toast.LENGTH_SHORT).show();

                                etTitle.setText("");
                                etDesc.setText("");
                                etTitle.setFocusable(true);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,
                            "Error !",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            etTitle.setError("Title cannot be empty");
            etDesc.setError("Description cannot be empty");
        }
    }

    // method updateData()
    public void updateData(String id, String title, String desc) {
        db.collection("Documents").document(id).update("key_title", title,
                "key_desc", desc )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Data updated successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Error !",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Fail",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}