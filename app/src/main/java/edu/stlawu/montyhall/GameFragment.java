package edu.stlawu.montyhall;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static edu.stlawu.montyhall.MainFragment.PREF_NAME;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    // Buttons for doors
    ImageButton door1button = null;
    ImageButton door2button = null;
    ImageButton door3button = null;
    Button resetButton = null;

    private boolean door1_tf = false;
    private boolean door2_tf = false;
    private boolean door3_tf = false;
    private boolean door1_clicked = false;
    private boolean door2_clicked = false;
    private boolean door3_clicked = false;

    MediaPlayer mpGoat;
    MediaPlayer mpWin;
    MediaPlayer mpLoss;


    TextView prompt, winView, winpView, lossView, losspView, totalView;

    //Counters
    private int wincounter = 0;
    private int losscounter = 0;
    private int totalcounter = 0;

    Random random_number = new Random();

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);

        // Get win count data
        SharedPreferences scoreWins = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        wincounter = scoreWins.getInt("WINS", 0);
        // Get Loss count data
        SharedPreferences scoreLoss = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        losscounter = scoreLoss.getInt("LOSSES", 0);
        // Get total count data
        SharedPreferences scoreTotal = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        totalcounter = scoreTotal.getInt("TOTALRUNS", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_game, container, false);

        //Initalize MediaPlayers for our sounds
        mpGoat = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.goatsound);
        mpWin = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.token);
        mpLoss = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.glass);

        // Initalize textViews
        winView = rootView.findViewById(R.id.win_count);
        winpView = rootView.findViewById(R.id.win_percentage);
        lossView = rootView.findViewById(R.id.loss_count);
        losspView = rootView.findViewById(R.id.loss_percentage);
        totalView = rootView.findViewById(R.id.total_count);
        prompt = rootView.findViewById(R.id.prompt);

        // Set scores
        winView.setText(String.valueOf(wincounter));
        lossView.setText(String.valueOf(losscounter));
        totalView.setText(String.valueOf(totalcounter));
        float winpercentage = (float)wincounter / (float)totalcounter;
        float losspercentage = (float)losscounter / (float)totalcounter;
        winpercentage = winpercentage * 100;
        losspercentage = losspercentage * 100;
        winpView.setText(String.format("%.2f", winpercentage) + "%");
        losspView.setText(String.format("%.2f", losspercentage) + "%");

        // Initialize buttons
        door1button = rootView.findViewById(R.id.door1);
        door2button = rootView.findViewById(R.id.door2);
        door3button = rootView.findViewById(R.id.door3);
        resetButton = rootView.findViewById(R.id.reset_button);

        // Button for the first door (most left)
        door1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change image to chosen door

                if (door2button.isEnabled() || door2_tf) {
                    door2button.setImageResource(R.drawable.closed_door);
                }
                if (door3button.isEnabled() || door3_tf){
                    door3button.setImageResource(R.drawable.closed_door);
                }

                door1button.setImageResource(R.drawable.closed_door_chosen);
                door1button.setEnabled(false);
                door1_tf = true;
                door1_clicked = true;

                if (door2_clicked) {
                    door2button.setEnabled(false);
                }
                if (door3_clicked) {
                    door3button.setEnabled(false);
                }


                if (door1_tf && !door2_tf && !door3_tf) {
                    Runnable run1 = new Runnable() {
                        @Override
                        public void run() {
                            // Get random number
                            int ran1 = random_number.nextInt(2) + 1;

                            // Play Goat sound
                            mpGoat.start();

                            // If r = 1, change 2nd door
                            if (ran1 == 1) {
                                door2button.setImageResource(R.drawable.goat);
                                door2button.setEnabled(false);

                                prompt.setText("3 Seconds To Pick Again.\n Choose New or Do Nothing.");

                                Runnable run2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 1 to door 3
                                        if (door3_tf) {
                                            door1_tf = false;
                                            door3button.setEnabled(false);
                                            door1button.setImageResource(R.drawable.closed_door);



                                            doorCountDown(door3button);

                                            Runnable doorDelay1 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    // Correct door was chosen (door 3)
                                                    int ran2 = random_number.nextInt(2) + 1;
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door3button.setImageResource(R.drawable.car);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }

                                                    // Wrong door was chosen (door 1)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door3button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }

                                                }
                                            };Handler d1 = new Handler();
                                            d1.postDelayed(doorDelay1, 3000);


                                        }
                                        // Player keeps choice
                                        else {

                                            doorCountDown(door1button);

                                            Runnable doorDelay2 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;
                                                    // Correct door was chosen (door 1)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door1button.setImageResource(R.drawable.car);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door3button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 3)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door1button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door3button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }
                                                }
                                            };
                                            Handler d2 = new Handler();
                                            d2.postDelayed(doorDelay2, 3000);
                                        }
                                    }
                                };
                                Handler h2 = new Handler();
                                h2.postDelayed(run2, 3000);
                            }
                            // If r = 2, change 3rd door
                            else {
                                door3button.setImageResource(R.drawable.goat);
                                door3button.setEnabled(false);
                                prompt.setText("3 Seconds To Pick Again.\n Choose New or Do Nothing.");

                                Runnable run2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 1 to door 2
                                        if (door2_tf) {
                                            door1_tf = false;
                                            door2button.setEnabled(false);
                                            door1button.setImageResource(R.drawable.closed_door);

                                            doorCountDown(door2button);

                                            Runnable doorDelay3 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;

                                                    // Correct door was chosen (door 2)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door2button.setImageResource(R.drawable.car);
                                                        door3button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 1)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door3button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }
                                                }
                                            };
                                            Handler d3 = new Handler();
                                            d3.postDelayed(doorDelay3, 3000);
                                        }
                                        // Player keeps choice
                                        else {

                                            doorCountDown(door1button);

                                            Runnable doorDelay4 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;

                                                    // Correct door was chosen (door 1)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door1button.setImageResource(R.drawable.car);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door3button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 2)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door3button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }
                                                }
                                            };
                                            Handler d4 = new Handler();
                                            d4.postDelayed(doorDelay4, 3000);
                                        }
                                    }
                                };
                                Handler h2 = new Handler();
                                h2.postDelayed(run2, 3000);
                            }
                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(run1, 500);
                }
            }
        });
        // Button for the second door (middle)
        door2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (door1button.isEnabled() || door1_tf) {
                    door1button.setImageResource(R.drawable.closed_door);
                }
                if (door3button.isEnabled() || door3_tf){
                    door3button.setImageResource(R.drawable.closed_door);
                }

                door2button.setImageResource(R.drawable.closed_door_chosen);
                door2button.setEnabled(false);
                door2_tf = true;
                door2_clicked = true;

                // Disable doors after picks are chosen
                if (door1_clicked) {
                    door1button.setEnabled(false);
                }
                if (door3_clicked) {
                    door3button.setEnabled(false);
                }

                if (door2_tf && !door1_tf && !door3_tf){
                    Runnable runn1 = new Runnable() {
                        @Override
                        public void run() {
                            // Get random number
                            int ran1 = random_number.nextInt(2) + 1;

                            // Play Goat sound
                            mpGoat.start();

                            // If r = 1, change 1st door
                            if (ran1 == 1) {
                                door1button.setImageResource(R.drawable.goat);
                                door1button.setEnabled(false);
                                prompt.setText("3 Seconds To Pick Again.\n Choose New or Do Nothing.");

                                Runnable runn2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 2 to door 3
                                        if (door3_tf) {
                                            door2_tf = false;
                                            door3button.setEnabled(false);
                                            door2button.setImageResource(R.drawable.closed_door);

                                            doorCountDown(door3button);

                                            Runnable doorDelay5 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;

                                                    // Correct door was chosen (door 3)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door3button.setImageResource(R.drawable.car);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 2)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door3button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }

                                                }
                                            };
                                            Handler d5 = new Handler();
                                            d5.postDelayed(doorDelay5, 3000);
                                        }
                                        // Player keeps choice
                                        else {

                                            doorCountDown(door2button);

                                            Runnable doorDelay6 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;
                                                    // Correct door was chosen (door 2)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door2button.setImageResource(R.drawable.car);
                                                        door1button.setImageResource(R.drawable.goat);
                                                        door3button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 3)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);
                                                        door3button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }

                                                }
                                            };
                                            Handler d6 = new Handler();
                                            d6.postDelayed(doorDelay6, 3000);
                                        }
                                    }
                                };
                                Handler h2 = new Handler();
                                h2.postDelayed(runn2, 3000);
                            }
                            // If r = 2, change 3rd door
                            else {
                                door3button.setImageResource(R.drawable.goat);
                                door3button.setEnabled(false);
                                prompt.setText("3 Seconds To Pick Again.\n Choose New or Do Nothing.");

                                Runnable runn2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 2 to door 1
                                        if (door1_tf) {
                                            door2_tf = false;
                                            door1button.setEnabled(false);
                                            door2button.setImageResource(R.drawable.closed_door);

                                            doorCountDown(door1button);

                                            Runnable doorDelay7 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;
                                                    // Correct door was chosen (door 1)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door1button.setImageResource(R.drawable.car);
                                                        door3button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 2)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door3button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }
                                                }
                                            };
                                            Handler d7 = new Handler();
                                            d7.postDelayed(doorDelay7, 3000);
                                        }
                                        // Player keeps choice
                                        else {
                                            doorCountDown(door2button);

                                            Runnable doorDelay8 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;
                                                    // Correct door was chosen (door 2)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door2button.setImageResource(R.drawable.car);
                                                        door1button.setImageResource(R.drawable.goat);
                                                        door3button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 1)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door3button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }
                                                }
                                            };
                                            Handler d8 = new Handler();
                                            d8.postDelayed(doorDelay8, 3000);
                                        }
                                    }
                                };
                                Handler h2 = new Handler();
                                h2.postDelayed(runn2, 3000);
                            }
                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(runn1, 500);
                }
            }
        });
        // Button for the third door (most right)
        door3button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (door2button.isEnabled() || door2_tf) {
                    door2button.setImageResource(R.drawable.closed_door);
                }
                if (door1button.isEnabled() || door1_tf){
                    door1button.setImageResource(R.drawable.closed_door);
                }
                door3button.setImageResource(R.drawable.closed_door_chosen);
                door3_tf = true;
                door3_clicked = true;
                door3button.setEnabled(false);

                // Disable doors after picks are chosen
                if (door1_clicked) {
                    door1button.setEnabled(false);
                }
                if (door2_clicked) {
                    door2button.setEnabled(false);
                }


                if (door3_tf && !door1_tf && !door2_tf){
                    Runnable runn1 = new Runnable() {
                        @Override
                        public void run() {
                            // Get random number
                            int ran1 = random_number.nextInt(2) + 1;

                            // Play goat sound
                            mpGoat.start();

                            // If r = 1, change 1st door
                            if (ran1 == 1) {
                                door1button.setImageResource(R.drawable.goat);
                                door1button.setEnabled(false);
                                prompt.setText("3 Seconds To Pick Again.\n Choose New or Do Nothing.");

                                Runnable runn2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 3 to door 2
                                        if (door2_tf) {
                                            door3_tf = false;
                                            door2button.setEnabled(false);
                                            door3button.setImageResource(R.drawable.closed_door);

                                            doorCountDown(door2button);

                                            Runnable doorDelay9 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;

                                                    // Correct door was chosen (door 2)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door2button.setImageResource(R.drawable.car);
                                                        door3button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 3)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);
                                                        door3button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }
                                                }
                                            };
                                            Handler d9 = new Handler();
                                            d9.postDelayed(doorDelay9, 3000);
                                        }
                                        // Player keeps choice
                                        else {
                                            doorCountDown(door3button);

                                            Runnable doorDelay10 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;
                                                    // Correct door was chosen (door 3)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door3button.setImageResource(R.drawable.car);
                                                        door1button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 2)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door3button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }
                                                }
                                            };
                                            Handler d10 = new Handler();
                                            d10.postDelayed(doorDelay10, 3000);
                                        }
                                    }
                                };
                                Handler h2 = new Handler();
                                h2.postDelayed(runn2, 3000);
                            }
                            // If r = 2, change 2nd door
                            else {
                                door2button.setImageResource(R.drawable.goat);
                                door2button.setEnabled(false);
                                prompt.setText("3 Seconds To Pick Again.\n Choose New or Do Nothing.");

                                Runnable run2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 3 to door 1
                                        if (door1_tf) {
                                            door3_tf = false;
                                            door1button.setEnabled(false);
                                            door3button.setImageResource(R.drawable.closed_door);

                                            doorCountDown(door1button);

                                            Runnable doorDelay11 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;

                                                    // Correct door was chosen (door 1)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door1button.setImageResource(R.drawable.car);
                                                        door3button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 3)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door1button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door3button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }
                                                }
                                            };
                                            Handler d11 = new Handler();
                                            d11.postDelayed(doorDelay11, 3000);
                                        }
                                        // Player keeps choice
                                        else {
                                            doorCountDown(door3button);

                                            Runnable doorDelay12 = new Runnable() {
                                                @Override
                                                public void run() {
                                                    int ran2 = random_number.nextInt(2) + 1;

                                                    // Correct door was chosen (door 3)
                                                    if (ran2 == 1) {
                                                        //Play Win Sound
                                                        mpWin.start();

                                                        door3button.setImageResource(R.drawable.car);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.goat);

                                                        incrementWinCounter();
                                                    }
                                                    // Wrong door was chosen (door 1)
                                                    else {
                                                        //Play Loss Sound
                                                        mpLoss.start();

                                                        door3button.setImageResource(R.drawable.goat);
                                                        door2button.setImageResource(R.drawable.goat);
                                                        door1button.setImageResource(R.drawable.car);

                                                        incrementLossCounter();
                                                    }
                                                }
                                            };
                                            Handler d12 = new Handler();
                                            d12.postDelayed(doorDelay12, 3000);
                                        }
                                    }
                                };
                                Handler h2 = new Handler();
                                h2.postDelayed(run2, 3000);
                            }
                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(runn1, 500);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences getStoredScores = getContext().
                getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        getStoredScores.getInt("WINS", 0);
        getStoredScores.getInt("LOSSES", 0);
        getStoredScores.getInt("TOTALRUNS", 0);

        winView.setText(String.valueOf(wincounter));
        lossView.setText(String.valueOf(losscounter));
        totalView.setText(String.valueOf(totalcounter));

        float losspercentage = (float)losscounter / (float)totalcounter;
        float winpercentage = (float)wincounter / (float)totalcounter;

        losspercentage = losspercentage*100;
        winpercentage = winpercentage * 100;

        losspView.setText(String.format("%.2f", losspercentage) + "%");
        winpView.setText(String.format("%.2f", winpercentage) + "%");



    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences.Editor scores = getActivity().
                getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();

        scores.putInt("WINS", wincounter).commit();
        scores.putInt("LOSSES", losscounter).commit();
        scores.putInt("TOTALRUNS", totalcounter).commit();
        float losspercentage = (float)losscounter / (float)totalcounter;
        float winpercentage = (float)wincounter / (float)totalcounter;

        losspercentage = losspercentage*100;
        winpercentage = winpercentage * 100;

        scores.putFloat("WINSP", losspercentage).commit();
        scores.putFloat("LOSSESP", winpercentage).commit();

    }

    public void incrementWinCounter () {
        prompt.setText("You Win!!");
        wincounter++;
        totalcounter++;
        winView.setText(String.valueOf(wincounter));
        totalView.setText(String.valueOf(totalcounter));

        float winpercentage = (float)wincounter / (float)totalcounter;
        float losspercentage = (float)losscounter / (float)totalcounter;

        winpercentage = winpercentage * 100;
        losspercentage = losspercentage * 100;

        winpView.setText(String.format("%.2f", winpercentage) + "%");
        losspView.setText(String.format("%.2f", losspercentage) + "%");
    }

    public void incrementLossCounter () {
        prompt.setText("You Lose!!");
        losscounter++;
        totalcounter++;
        lossView.setText(String.valueOf(losscounter));
        totalView.setText(String.valueOf(totalcounter));

        float losspercentage = (float)losscounter / (float)totalcounter;
        float winpercentage = (float)wincounter / (float)totalcounter;

        losspercentage = losspercentage*100;
        winpercentage = winpercentage * 100;

        losspView.setText(String.format("%.2f", losspercentage) + "%");
        winpView.setText(String.format("%.2f", winpercentage) + "%");

    }

    public void reset() {
        door1button.setEnabled(true);
        door2button.setEnabled(true);
        door3button.setEnabled(true);

        door1button.setImageResource(R.drawable.closed_door);
        door2button.setImageResource(R.drawable.closed_door);
        door3button.setImageResource(R.drawable.closed_door);

        door1_tf = false;
        door2_tf = false;
        door3_tf = false;

        door1_clicked = false;
        door2_clicked = false;
        door3_clicked = false;

        prompt.setText("Choose a door");

    }

    public void doorCountDown(final ImageButton chosenDoor) {
        chosenDoor.setImageResource(R.drawable.three);
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                chosenDoor.setImageResource(R.drawable.two);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r1, 1000);

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                chosenDoor.setImageResource(R.drawable.one);
            }
        };
        Handler h2 = new Handler();
        h2.postDelayed(r2, 2000);
    }




}
