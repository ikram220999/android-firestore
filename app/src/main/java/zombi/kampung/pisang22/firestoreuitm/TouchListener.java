package zombi.kampung.pisang22.firestoreuitm;

import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TouchListener extends ItemTouchHelper.SimpleCallback {
    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link #getSwipeDirs(RecyclerView, ViewHolder)}
     * and / or {@link #getDragDirs(RecyclerView, ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     */
    private MyAdapter adapter;

    //constructor
    public TouchListener(MyAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    // decorate recyclerview using recyclerview-swiperdecorator library
    // enable Jetifier use to migrate an android project

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState,
                isCurrentlyActive)
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                .addSwipeLeftBackgroundColor(Color.RED)
                .addSwipeRightBackgroundColor(Color.GRAY)
                .create()
                .decorate();
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // get the item position/index
        int position = viewHolder.getAdapterPosition();

        // chheck direction,
        if(direction == ItemTouchHelper.LEFT) {
            // if left -> delete
            adapter.deleteData(position);
            adapter.notifyDataSetChanged();
        } else {
            //  i right -> edit
            adapter.updateData(position);
            adapter.notifyDataSetChanged();
        }
    }
}
