package com.example.testapptodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by Jacob on 2/27/2016.
 */
public class PlanAdapter extends ArrayAdapter<Plan> {

   // public final static String EXTRA_ID = "PlanAdapter.ID";
    private MainActivity parentAct;

    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public PlanAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    public void SetActivity(MainActivity p) {
        parentAct = p;
    }



    /**
     * Returns the view for a specific item on the list
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final Plan currentPlan = getItem(position);


        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentPlan);
        final Button button = (Button) row.findViewById(R.id.planItem);

        button.setText(currentPlan.getmName());

        button.setWidth(475);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String workoutID = currentPlan.getmId();
                parentAct.ViewPlan(workoutID);
            }
        });
        return row;
    }

}
