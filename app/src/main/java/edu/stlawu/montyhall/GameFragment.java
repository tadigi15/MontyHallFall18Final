package edu.stlawu.montyhall;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    // Counters
    //private TextView win_count = null;


    // Buttons for doors
    ImageButton door1button = null;
    ImageButton door2button = null;
    ImageButton door3button = null;

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

        final TextView winView = rootView.findViewById(R.id.win_count);


        // Initalize door buttons
        door1button = rootView.findViewById(R.id.door1);
        door2button = rootView.findViewById(R.id.door2);
        door3button = rootView.findViewById(R.id.door3);

        // Button for the first door (most left)
        door1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change image to chosen door
                door1button.setImageResource(R.drawable.closed_door_chosen);

                // Get random number
                int r1 = random_number.nextInt(2) + 1;

                // If r = 1, change 2nd door
                if (r1 == 1) {
                    door2button.setImageResource(R.drawable.goat);
                }
                // If r = 2, change 3rd door
                else if (r1 == 2) {
                    door3button.setImageResource(R.drawable.goat);
                }

                // Set texts
                //winView.setText(String.valueOf(r1));

            }
        });



        // Button for the second door (middle)

        door2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                door2button.setImageResource(R.drawable.closed_door_chosen);

                // Get random number
                int r1 = random_number.nextInt(2) + 1;

                // If r = 1, change 1st door
                if (r1 == 1) {
                    door1button.setImageResource(R.drawable.goat);
                }
                // If r = 2, change 3rd door
                else if (r1 == 2) {
                    door3button.setImageResource(R.drawable.goat);
                }
            }
        });

        // Button for the third door (most right)
        door3button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                door3button.setImageResource(R.drawable.closed_door_chosen);

                // Get random number
                int r1 = random_number.nextInt(2) + 1;

                // If r = 1, change 1st door
                if (r1 == 1) {
                    door1button.setImageResource(R.drawable.goat);
                }
                // If r = 2, change 2nd door
                else if (r1 == 2) {
                    door2button.setImageResource(R.drawable.goat);
                }
            }
        });


        return rootView;
    }

}
