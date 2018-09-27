package edu.stlawu.montyhall;


import android.media.Image;
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
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_game, container, false);

        // Initalize textViews
        winView = rootView.findViewById(R.id.win_count);
        winpView = rootView.findViewById(R.id.win_percentage);
        lossView = rootView.findViewById(R.id.loss_count);
        losspView = rootView.findViewById(R.id.loss_percentage);
        totalView = rootView.findViewById(R.id.total_count);
        prompt = rootView.findViewById(R.id.prompt);


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
                if (door2button.isEnabled()) {
                    door2button.setImageResource(R.drawable.closed_door);
                }
                if (door3button.isEnabled()){
                    door3button.setImageResource(R.drawable.closed_door);
                }
                door1button.setImageResource(R.drawable.closed_door_chosen);
                door1_tf = true;

                if (door1_tf && !door2_tf && !door3_tf) {
                    Runnable run1 = new Runnable() {
                        @Override
                        public void run() {
                            // Get random number
                            int ran1 = random_number.nextInt(2) + 1;

                            // If r = 1, change 2nd door
                            if (ran1 == 1) {
                                door2button.setImageResource(R.drawable.goat);
                                door2button.setEnabled(false);
                                prompt.setText("3 Seconds To Pick Again.");

                                Runnable run2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 1 to door 3
                                        if (door3_tf) {
                                            door1_tf = false;
                                            door3button.setEnabled(false);
                                            door1button.setImageResource(R.drawable.closed_door);

                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 3)
                                            if (ran2 == 1) {
                                                door3button.setImageResource(R.drawable.car);
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }

                                            // Wrong door was chosen (door 1)
                                            else {
                                                door3button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
                                        }
                                        // Player keeps choice
                                        else {
                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 1)
                                            if (ran2 == 1) {
                                                door1button.setImageResource(R.drawable.car);
                                                door2button.setImageResource(R.drawable.goat);
                                                door3button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 3)
                                            else {
                                                door1button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);
                                                door3button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
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
                                prompt.setText("3 Seconds To Pick Again.");

                                Runnable run2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 1 to door 2
                                        if (door2_tf) {
                                            door1_tf = false;
                                            door2button.setEnabled(false);
                                            door1button.setImageResource(R.drawable.closed_door);

                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 2)
                                            if (ran2 == 1) {
                                                door2button.setImageResource(R.drawable.car);
                                                door3button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 1)
                                            else {
                                                door3button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
                                        }
                                        // Player keeps choice
                                        else {
                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 1)
                                            if (ran2 == 1) {
                                                door1button.setImageResource(R.drawable.car);
                                                door2button.setImageResource(R.drawable.goat);
                                                door3button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 2)
                                            else {
                                                door3button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
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
                if (door1button.isEnabled()) {
                    door1button.setImageResource(R.drawable.closed_door);
                }
                if (door3button.isEnabled()){
                    door3button.setImageResource(R.drawable.closed_door);
                }

                door2button.setImageResource(R.drawable.closed_door_chosen);
                door2_tf = true;

                if (door2_tf && !door1_tf && !door3_tf){
                    Runnable runn1 = new Runnable() {
                        @Override
                        public void run() {
                            // Get random number
                            int ran1 = random_number.nextInt(2) + 1;

                            // If r = 1, change 1st door
                            if (ran1 == 1) {
                                door1button.setImageResource(R.drawable.goat);
                                door1button.setEnabled(false);
                                prompt.setText("3 Seconds To Pick Again.");

                                Runnable runn2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 2 to door 3
                                        if (door3_tf) {
                                            door2_tf = false;
                                            door3button.setEnabled(false);
                                            door2button.setImageResource(R.drawable.closed_door);

                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 3)
                                            if (ran2 == 1) {
                                                door3button.setImageResource(R.drawable.car);
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 2)
                                            else {
                                                door3button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
                                        }
                                        // Player keeps choice
                                        else {
                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 2)
                                            if (ran2 == 1) {
                                                door2button.setImageResource(R.drawable.car);
                                                door1button.setImageResource(R.drawable.goat);
                                                door3button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 2)
                                            else {
                                                door3button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
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
                                prompt.setText("3 Seconds To Pick Again.");

                                Runnable runn2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 2 to door 1
                                        if (door1_tf) {
                                            door2_tf = false;
                                            door1button.setEnabled(false);
                                            door2button.setImageResource(R.drawable.closed_door);

                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 1)
                                            if (ran2 == 1) {
                                                door1button.setImageResource(R.drawable.car);
                                                door3button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 2)
                                            else {
                                                door3button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
                                        }
                                        // Player keeps choice
                                        else {
                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 2)
                                            if (ran2 == 1) {
                                                door2button.setImageResource(R.drawable.car);
                                                door1button.setImageResource(R.drawable.goat);
                                                door3button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 1)
                                            else {
                                                door3button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
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
                if (door2button.isEnabled()) {
                    door2button.setImageResource(R.drawable.closed_door);
                }
                if (door1button.isEnabled()){
                    door1button.setImageResource(R.drawable.closed_door);
                }
                door3button.setImageResource(R.drawable.closed_door_chosen);
                door3_tf = true;

                if (door3_tf && !door1_tf && !door2_tf){
                    Runnable runn1 = new Runnable() {
                        @Override
                        public void run() {
                            // Get random number
                            int ran1 = random_number.nextInt(2) + 1;

                            // If r = 1, change 1st door
                            if (ran1 == 1) {
                                door1button.setImageResource(R.drawable.goat);
                                door1button.setEnabled(false);
                                prompt.setText("3 Seconds To Pick Again.");

                                Runnable runn2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 3 to door 2
                                        if (door2_tf) {
                                            door3_tf = false;
                                            door2button.setEnabled(false);
                                            door3button.setImageResource(R.drawable.closed_door);

                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 2)
                                            if (ran2 == 1) {
                                                door2button.setImageResource(R.drawable.car);
                                                door3button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 3)
                                            else {
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.goat);
                                                door3button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
                                        }
                                        // Player keeps choice
                                        else {
                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 3)
                                            if (ran2 == 1) {
                                                door3button.setImageResource(R.drawable.car);
                                                door1button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 1)
                                            else {
                                                door3button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
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
                                prompt.setText("3 Seconds To Pick Again.");

                                Runnable run2 = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Player Switches Pick from door 3 to door 1
                                        if (door1_tf) {
                                            door3_tf = false;
                                            door1button.setEnabled(false);
                                            door3button.setImageResource(R.drawable.closed_door);

                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 1)
                                            if (ran2 == 1) {
                                                door1button.setImageResource(R.drawable.car);
                                                door3button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 3)
                                            else {
                                                door1button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);
                                                door3button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
                                        }
                                        // Player keeps choice
                                        else {
                                            int ran2 = random_number.nextInt(2) + 1;

                                            // Correct door was chosen (door 3)
                                            if (ran2 == 1) {
                                                door3button.setImageResource(R.drawable.car);
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.goat);

                                                incrementWinCounter(prompt, winView, totalView, winpView);
                                            }
                                            // Wrong door was chosen (door 1)
                                            else {
                                                door3button.setImageResource(R.drawable.goat);
                                                door2button.setImageResource(R.drawable.goat);
                                                door1button.setImageResource(R.drawable.car);

                                                incrementLossCounter(prompt, lossView, totalView, losspView);
                                            }
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

    public void incrementWinCounter (TextView prompt, TextView winView, TextView totalView, TextView winpView) {
        prompt.setText("You Win!!");
        wincounter++;
        totalcounter++;
        winView.setText(String.valueOf(wincounter));
        totalView.setText(String.valueOf(totalcounter));
        winpView.setText(String.valueOf(wincounter / totalcounter));
    }

    public void incrementLossCounter (TextView prompt, TextView lossView, TextView totalView, TextView losspView) {
        prompt.setText("You Lose!!");
        losscounter++;
        totalcounter++;
        lossView.setText(String.valueOf(losscounter));
        totalView.setText(String.valueOf(totalcounter));
        losspView.setText(String.valueOf(losscounter / totalcounter));

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

        prompt.setText("Choose a door");




    }




}
