package com.xbank.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity {

    private TextView setup, punchline;
    private ProgressBar progressBar;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://official-joke-api.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private JokeDb jokeDb;

    JokeApiInterface apiInterface =
            retrofit.create(JokeApiInterface.class);

    private LiveData<List<Joke>> jokeListLiveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        progressBar = findViewById(R.id.progressBar);

        setup = (TextView) findViewById(R.id.setup);
        punchline = (TextView) findViewById(R.id.punchline);

        jokeDb = Room.databaseBuilder(
                getApplicationContext(),JokeDb.class,"jokedb").build();

        jokeListLiveData = jokeDb.jokeDAO().getAllJokes();

        jokeListLiveData.observe(this, new Observer<List<Joke>>() {
            @Override
            public void onChanged(List<Joke> jokes) {
                Log.d("LIVEDATA", "onChanged: "+jokes.toString());
            }
        });

    }

    public void startLongTask(View view) {
        //we can pass an integer array
        // Integer[] integerArray = new Integer[]{1,2};
        //or a single integer value
        //new SleepClass().execute(10);
        //SleepClass sleepClass = new SleepClass();
        //sleepClass.execute(10);

        progressBar.setIndeterminate(true);

        apiInterface.getAJoke().enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                progressBar.setIndeterminate(false);
                if(response.code()==200){
                    Joke joke = response.body();

                    setup.setText(joke.getSetup());
                    punchline.setText(joke.getPunchline());

                    saveJoke(joke);

                    Log.d("WelcomeResponded",joke.toString());
                }else{
                    Toast.makeText(WelcomeActivity.this, "Response was not success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Log.d("WelcomeError",t.getMessage());
                progressBar.setIndeterminate(false);
            }
        });

    }


    private void saveJoke(Joke joke){
        new DbInfo().execute(joke);
    }

    class DbInfo extends AsyncTask<Joke,Void,String>{

        @Override
        protected String doInBackground(Joke... jokes) {

            Joke joke = jokes[0];
            try {
                jokeDb.jokeDAO().insert(joke);
                return "Joke saved successfully";

            }catch (Exception e){
                Log.d("insert","insertion failed");
                return "Joke failed to save";

            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(WelcomeActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }




    class SleepClass extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            progressBar.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer progressvalue = values[0];
            progressBar.setProgress(progressvalue);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            Integer numberofintervals = integers[0];
            for (Integer i = 0; i <= numberofintervals; i++) {
                publishProgress(i * 100 / numberofintervals);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Loading completed";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(WelcomeActivity.this, result, Toast.LENGTH_SHORT).show();


        }
    }
}

   /* class SleepClass extends AsyncTask<Integer,Integer,String>{


        //pre execute is the first method that will be run when call SleepClass().execute()
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(0);
        }

        //doInBackground is then called on a background thread
        //do in background contains the array or value we passed in the execute function
        //if we passed a single value, it is stored as the first element in the array
        @Override
        protected String doInBackground(Integer... integers) {
            Integer numberOfIntervals = integers[0];

            //we want to simulate a long running task
            //we also want the task to update us every second with progress
            for(int i=0;i<numberOfIntervals;i++){
                //publishprogress will call onProgressUpdate on the main/ui thread
                //we can pass an integer array or a single integer value

                publishProgress(i*100/numberOfIntervals);


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //this string will be sent to the onPostExecute method
            return "Long task done";
        }

        //onProgressUpdate will happen on the main/ui thread
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        //onPostExecute will happen on the main/ui thread
        //last method to be called when background work is done
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setProgress(100);
            Toast.makeText(WelcomeActivity.this, s, Toast.LENGTH_SHORT).show();
        }


    }*/

