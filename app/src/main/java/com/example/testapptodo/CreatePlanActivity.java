package com.example.testapptodo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testapptodo.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CreatePlanActivity extends Activity {

    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    //private EditText mTextSuccess;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<Plan> mPlanTable;

    // Create a new item
    final private Plan plan = new Plan("",0,0,0,0,0,0,0,0, "Jacob");

    Spinner spinner1;
    EditText reps1;
    EditText sets1;
    EditText run1;

    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        //mTextSuccess = (EditText) findViewById(R.id.textSuccess);

        try {
            mClient = new MobileServiceClient(
                    "https://testapptodo.azurewebsites.net",
                    this);

            mPlanTable = mClient.getTable(Plan.class);

            initLocalStore().get();

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e){
            createAndShowDialog(e, "Error");
        }

        spinner1 = (Spinner)findViewById(R.id.Exercise1);
        reps1 = (EditText) findViewById(R.id.reps1);
        sets1 = (EditText) findViewById(R.id.sets1);
        run1 = (EditText) findViewById(R.id.run1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                text = spinner1.getSelectedItem().toString();

                if (text.equals("Run")) {
                    reps1.setVisibility(View.INVISIBLE);
                    sets1.setVisibility(View.INVISIBLE);
                    run1.setVisibility(View.VISIBLE);

                    run1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            plan.setmRunningTime(Integer.parseInt(s.toString()));
                        }
                    });
                }
                else {
                    reps1.setVisibility(View.VISIBLE);
                    sets1.setVisibility(View.VISIBLE);
                    run1.setVisibility(View.INVISIBLE);

                    sets1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatSets(Integer.parseInt(s.toString()));
                            }
                        }
                    });

                    reps1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatReps(Integer.parseInt(s.toString()));
                            }
                        }
                    });
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_plan, menu);
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

    public void showRow2(View view) {
        final Spinner spinner2 = (Spinner)findViewById(R.id.Exercise2);
        final EditText reps2 = (EditText) findViewById(R.id.reps2);
        final EditText sets2 = (EditText) findViewById(R.id.sets2);
        final EditText run2 = (EditText) findViewById(R.id.run2);
        final Button add2 = (Button) findViewById(R.id.add2);

        spinner2.setVisibility(View.VISIBLE);
        reps2.setVisibility(View.VISIBLE);
        sets2.setVisibility(View.VISIBLE);
        add2.setVisibility(View.VISIBLE);
        run2.setVisibility(View.INVISIBLE);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                text = spinner2.getSelectedItem().toString();

                if (text.equals("Run")) {
                    reps2.setVisibility(View.INVISIBLE);
                    sets2.setVisibility(View.INVISIBLE);
                    run2.setVisibility(View.VISIBLE);

                    run2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            plan.setmRunningTime(Integer.parseInt(s.toString()));
                        }
                    });
                }
                else {
                    reps2.setVisibility(View.VISIBLE);
                    sets2.setVisibility(View.VISIBLE);
                    run2.setVisibility(View.INVISIBLE);

                    sets2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatSets(Integer.parseInt(s.toString()));
                            }
                        }
                    });

                    reps2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatReps(Integer.parseInt(s.toString()));
                            }
                        }
                    });
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void showRow3(View view) {
        final Spinner spinner3 = (Spinner)findViewById(R.id.Exercise3);
        final EditText reps3 = (EditText) findViewById(R.id.reps3);
        final EditText sets3 = (EditText) findViewById(R.id.sets3);
        final EditText run3 = (EditText) findViewById(R.id.run3);
        final Button add3 = (Button) findViewById(R.id.add3);

        spinner3.setVisibility(View.VISIBLE);
        reps3.setVisibility(View.VISIBLE);
        sets3.setVisibility(View.VISIBLE);
        add3.setVisibility(View.VISIBLE);
        run3.setVisibility(View.INVISIBLE);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                text = spinner3.getSelectedItem().toString();

                if (text.equals("Run")) {
                    reps3.setVisibility(View.INVISIBLE);
                    sets3.setVisibility(View.INVISIBLE);
                    run3.setVisibility(View.VISIBLE);

                    run3.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            plan.setmRunningTime(Integer.parseInt(s.toString()));
                        }
                    });
                }
                else {
                    reps3.setVisibility(View.VISIBLE);
                    sets3.setVisibility(View.VISIBLE);
                    run3.setVisibility(View.INVISIBLE);

                    sets3.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatSets(Integer.parseInt(s.toString()));
                            }
                        }
                    });

                    reps3.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatReps(Integer.parseInt(s.toString()));
                            }
                        }
                    });
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void showRow4(View view) {
        final Spinner spinner4 = (Spinner)findViewById(R.id.Exercise4);
        final EditText reps4 = (EditText) findViewById(R.id.reps4);
        final EditText sets4 = (EditText) findViewById(R.id.sets4);
        final EditText run4 = (EditText) findViewById(R.id.run4);
        final Button add4 = (Button) findViewById(R.id.add4);

        spinner4.setVisibility(View.VISIBLE);
        reps4.setVisibility(View.VISIBLE);
        sets4.setVisibility(View.VISIBLE);
        add4.setVisibility(View.VISIBLE);
        run4.setVisibility(View.INVISIBLE);

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                text = spinner4.getSelectedItem().toString();

                if (text.equals("Run")) {
                    reps4.setVisibility(View.INVISIBLE);
                    sets4.setVisibility(View.INVISIBLE);
                    run4.setVisibility(View.VISIBLE);

                    run4.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            plan.setmRunningTime(Integer.parseInt(s.toString()));
                        }
                    });
                }
                else {
                    reps4.setVisibility(View.VISIBLE);
                    sets4.setVisibility(View.VISIBLE);
                    run4.setVisibility(View.INVISIBLE);

                    sets4.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatSets(Integer.parseInt(s.toString()));
                            }
                        }
                    });

                    reps4.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatReps(Integer.parseInt(s.toString()));
                            }
                        }
                    });
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void showRow5(View view) {
        final Spinner spinner5 = (Spinner)findViewById(R.id.Exercise5);
        final EditText reps5 = (EditText) findViewById(R.id.reps5);
        final EditText sets5 = (EditText) findViewById(R.id.sets5);
        final EditText run5 = (EditText) findViewById(R.id.run5);

        spinner5.setVisibility(View.VISIBLE);
        reps5.setVisibility(View.VISIBLE);
        sets5.setVisibility(View.VISIBLE);
        run5.setVisibility(View.INVISIBLE);

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                text = spinner5.getSelectedItem().toString();

                if (text.equals("Run")) {
                    reps5.setVisibility(View.INVISIBLE);
                    sets5.setVisibility(View.INVISIBLE);
                    run5.setVisibility(View.VISIBLE);

                    run5.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            plan.setmRunningTime(Integer.parseInt(s.toString()));
                        }
                    });
                }
                else {
                    reps5.setVisibility(View.VISIBLE);
                    sets5.setVisibility(View.VISIBLE);
                    run5.setVisibility(View.INVISIBLE);

                    sets5.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpSets(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatSets(Integer.parseInt(s.toString()));
                            }
                        }
                    });

                    reps5.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {}
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (text.equals("Push Ups")) {
                                plan.setmPushUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Sit Ups")) {
                                plan.setmSitUpReps(Integer.parseInt(s.toString()));
                            }
                            else if (text.equals("Squats")) {
                                plan.setmSquatReps(Integer.parseInt(s.toString()));
                            }
                        }
                    });
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void submit(View view) {
        if (mClient == null) {
            return;
        }

        final Intent intent = new Intent(this, MainActivity.class);

        EditText name = (EditText) findViewById(R.id.workoutName);
        plan.setmName(name.getText().toString());

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Plan entity = addItemInTable(plan);

                    startActivity(intent);
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };

        runAsyncTask(task);

        //mTextSuccess.setText("Workout created!");
    }

    /**
     * Add an item to the Mobile Service Table
     *
     * @param item
     *            The item to Add
     */
    public Plan addItemInTable(Plan item) throws ExecutionException, InterruptedException {
        Plan entity = mPlanTable.insert(item).get();
        return entity;
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


}
