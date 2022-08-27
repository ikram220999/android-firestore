package zombi.kampung.pisang22.firestoreuitm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private MyAdapter adapter;
    private ArrayList<Model> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize db object, myList
        db = FirebaseFirestore.getInstance();
        myList = new ArrayList<>();
        adapter = new MyAdapter(this, myList);

        // set adapter to recyclerview
        recyclerView.setAdapter(adapter);

        // add touch listener
        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchListener(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

        // creeate method showData()
        showData();

    }

    public void showData() {
        //get all data in the collection
        db.collection("Documents").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        myList.clear();

                        // documentsnapshot class access to the field in database
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            Model model = new Model(snapshot.getString("key_id"),
                                    snapshot.getString("key_title"),
                                    snapshot.getString("key_desc"));
                            myList.add(model);
                        }

                        // notify adapter the changes
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowActivity.this, "Something wrong !",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}