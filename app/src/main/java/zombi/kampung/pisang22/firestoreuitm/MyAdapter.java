package zombi.kampung.pisang22.firestoreuitm;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    // variable declaration
    private List<Model> myList;
    private ShowActivity myContext;

    public MyAdapter( ShowActivity myContext, List<Model> myList) {
        this.myList = myList;
        this.myContext = myContext;
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title_text, desc_text;
        CardView cardView;
        private ShowActivity ctx;

        // constructor
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title_text = itemView.findViewById(R.id.tvTitle);
            desc_text = itemView.findViewById(R.id.tvDesc);
            cardView = itemView.findViewById(R.id.cardView);



        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout itm to parent (recyclerview)
        View v = LayoutInflater.from(myContext).inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // bind dataset to holder
        holder.title_text.setText(myList.get(position).getTitle());
        holder.desc_text.setText(myList.get(position).getDesc());

    }

    @Override
    public int getItemCount() {

        return myList.size();
    }

    // create method for update & delete item from myList in recyclerview
    public void updateData(int position) {

        Model model = myList.get(position);

        // create container to hold data
        Bundle container = new Bundle();
        // put data to container
        container.putString("key_id", model.getId());
        container.putString("key_title", model.getTitle());
        container.putString("key_desc", model.getDesc());

        // attach container to intent
        Intent i = new Intent(myContext, MainActivity.class);
        i.putExtras(container);

        myContext.startActivity(i);

    }

    // create deletedata()
    public void deleteData(int position) {
        Model item = myList.get(position);

        db.collection("Documents").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            // notify adapter that some data has been deleted
                            myList.remove(position);
                            notifyItemRemoved(position);
                            myContext.showData();


                            Toast.makeText(myContext, "Data deleted successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(myContext, "Error !",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }





}
