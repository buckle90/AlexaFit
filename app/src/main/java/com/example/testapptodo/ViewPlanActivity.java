package com.example.testapptodo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.testapptodo.R;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class ViewPlanActivity extends Activity {

    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<Plan> mPlanTable;


    public final static String EXTRA_ID = "MainActivity.ID";

    String workoutID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan);

        if (getIntent().hasExtra(EXTRA_ID)){
            workoutID = getIntent().getStringExtra(EXTRA_ID);

        }

        try {
            mClient = new MobileServiceClient(
                    "https://testapptodo.azurewebsites.net",
                    this);

            mPlanTable = mClient.getTable(Plan.class);

            initLocalStore().get();

            GetPlan(workoutID);
//            //Plan plan = GetPlan(workoutID).get(0);
//
//            EditText nameText = (EditText) findViewById(R.id.name);
//            nameText.setText(plan.getmName());

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e){
            createAndShowDialog(e, "Error");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {

            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable e) {
                    resultFuture.setException(e);
                }

                @Override
                public void onSuccess(ServiceFilterResponse response) {
                    resultFuture.set(response);
                }
            });

            return resultFuture;
        }
    }

    /**
     * Initialize local storage
     * @return
     * @throws com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException
     * @throws java.util.concurrent.ExecutionException
     * @throws InterruptedException
     */
    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("text", ColumnDataType.String);
                    tableDefinition.put("complete", ColumnDataType.Boolean);

                    localStore.defineTable("ToDoItem", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }


    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    /**
     * Run an ASync task on the corresponding executor
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }


    /**
     * Refresh the list with the items in the Table
     */
    private void GetPlan(String id) {
        final String idString = id;


        // Get the items that weren't marked as completed and add them in the
        // adapter

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {

                    final List<Plan> results = mPlanTable.where().field("id").eq(idString).execute().get();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Plan plan = results.get(0);
                            TextView nameText = (TextView) findViewById(R.id.planName);
                            nameText.setText(plan.getmName());

                            TextView pushUpLabel = (TextView) findViewById(R.id.pushUpLabel);
                            TextView pushUpReps = (TextView) findViewById(R.id.pushUpReps);
                            TextView pushUpSets = (TextView) findViewById(R.id.pushUpSets);
                            if (plan.getmPushUpReps() > 0 && plan.getmPushUpSets() > 0) {
                                pushUpLabel.setText("Push Ups:");
                                pushUpReps.setText("Reps: " + Integer.toString(plan.getmPushUpReps()));
                                pushUpSets.setText("Sets: " + Integer.toString(plan.getmPushUpSets()));
                            }
                            else {
                                ViewGroup.LayoutParams params = pushUpLabel.getLayoutParams();
                                params.height = 0;
                                pushUpLabel.setLayoutParams(params);
                                params = pushUpReps.getLayoutParams();
                                params.height = 0;
                                pushUpReps.setLayoutParams(params);
                                params = pushUpSets.getLayoutParams();
                                params.height = 0;
                                pushUpSets.setLayoutParams(params);
                            }

                            TextView sitUpLabel = (TextView) findViewById(R.id.sitUpLabel);
                            TextView sitUpReps = (TextView) findViewById(R.id.sitUpReps);
                            TextView sitUpSets = (TextView) findViewById(R.id.sitUpSets);
                            if (plan.getmSitUpReps() > 0 && plan.getmSitUpSets() > 0) {
                                sitUpLabel.setText("Sit Ups:");
                                sitUpReps.setText("Reps: " + Integer.toString(plan.getmSitUpReps()));
                                sitUpSets.setText("Sets: " + Integer.toString(plan.getmSitUpSets()));
                            }
                            else {
                                ViewGroup.LayoutParams params = sitUpLabel.getLayoutParams();
                                params.height = 0;
                                sitUpLabel.setLayoutParams(params);
                                params = sitUpReps.getLayoutParams();
                                params.height = 0;
                                sitUpReps.setLayoutParams(params);
                                params = sitUpSets.getLayoutParams();
                                params.height = 0;
                                sitUpSets.setLayoutParams(params);
                            }

                            TextView squatLabel = (TextView) findViewById(R.id.squatLabel);
                            TextView squatReps = (TextView) findViewById(R.id.squatReps);
                            TextView squatSets = (TextView) findViewById(R.id.squatSets);
                            if (plan.getmSquatReps() > 0 && plan.getmSquatSets() > 0) {
                                squatLabel.setText("Squats:");
                                squatReps.setText("Reps: " + Integer.toString(plan.getmSquatReps()));
                                squatSets.setText("Sets: " + Integer.toString(plan.getmSquatSets()));
                            }
                            else {
                                ViewGroup.LayoutParams params = squatLabel.getLayoutParams();
                                params.height = 0;
                                squatLabel.setLayoutParams(params);
                                params = squatReps.getLayoutParams();
                                params.height = 0;
                                squatReps.setLayoutParams(params);
                                params = squatSets.getLayoutParams();
                                params.height = 0;
                                squatSets.setLayoutParams(params);
                            }

                            TextView runLabel = (TextView) findViewById(R.id.runLabel);
                            TextView runTime = (TextView) findViewById(R.id.runTime);
                            if (plan.getmRunningTime() > 0) {
                                runLabel.setText("Running:");
                                runTime.setText("Time: " + Integer.toString(plan.getmRunningTime()));
                            }
                            else {
                                ViewGroup.LayoutParams params = runLabel.getLayoutParams();
                                params.height = 0;
                                runLabel.setLayoutParams(params);
                                params = runTime.getLayoutParams();
                                params.height = 0;
                                runTime.setLayoutParams(params);
                            }

                            TextView restLabel = (TextView) findViewById(R.id.restLabel);
                            TextView restTime = (TextView) findViewById(R.id.restTime);
                            if (plan.getmRestTime() > 0) {
                                restLabel.setText("Rest:");
                                restTime.setText("Time: " + Integer.toString(plan.getmRestTime()));
                            }
                            else {
                                ViewGroup.LayoutParams params = restLabel.getLayoutParams();
                                params.height = 0;
                                restLabel.setLayoutParams(params);
                                params = restTime.getLayoutParams();
                                params.height = 0;
                                restTime.setLayoutParams(params);
                            }
                        }
                    });


                } catch (final Exception e){
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);
    }


    /**
     * Refresh the list with the items in the Table
     */
//    private void refreshItemsFromTable() {
//
//        // Get the items that weren't marked as completed and add them in the
//        // adapter
//
//        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                try {
//                    final List<Plan> results = refreshItemsFromMobileServiceTable();
//                    Log.d("FARTS", results.toString());
//
//                    //Offline Sync
//                    //final List<ToDoItem> results = refreshItemsFromMobileServiceTableSyncTable();
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mAdapter.clear();
//
//                            for (Plan item : results) {
//                                mAdapter.add(item);
//                            }
//                        }
//                    });
//                } catch (final Exception e){
//                    createAndShowDialogFromTask(e, "Error");
//                }
//
//                return null;
//            }
//        };
//
//        runAsyncTask(task);
//    }

    /**
     * Refresh the list with the items in the Mobile Service Table
     */
//    private List<Plan> GetPlan(String id) throws ExecutionException, InterruptedException {
//
////        return mToDoTable.where().field("complete").
////                eq(val(false)).execute().get();
//
//        List<Plan> res = mPlanTable.where().field("id")
//                .eq("91d2573e-8eaf-4954-bd41-fb9ffc3d567d").execute().get();
//        return res;
//    }
}
