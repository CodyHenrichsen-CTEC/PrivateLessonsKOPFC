package kopfc.privatelessons.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import kopfc.privatelessons.model.Instructor;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BiosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BiosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BiosFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<Instructor> instructorList;
    private ArrayList<Instructor> filteredList;
    private InstructorAdapter biosAdapter;
    private ListView instructorListView;
    private RadioGroup timeGroup, dayGroup, strokeGroup;

    private Button clearSelectionButton;
    private boolean clicked;

    public BiosFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BiosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BiosFragment newInstance(String param1, String param2)
    {
        BiosFragment fragment = new BiosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void openBiosFile()
    {
        //Open instructor.csv
        AssetManager instructorFileManager = getActivity().getApplicationContext().getResources().getAssets();
        InputStream fileInput;
        String fileAsString = "";
        try
        {
            fileInput = instructorFileManager.open("bios.csv");
            BufferedReader input = new BufferedReader(new InputStreamReader(fileInput, "UTF-8"));
            StringBuilder listBuffer = new StringBuilder();
            String readLine = input.readLine();
            while(readLine  != null)
            {

                String [] instructor = readLine.split(",");
                boolean [] availability = new boolean[12];
                boolean [] strokes = new boolean[4];
                String name = instructor[0] + " " + instructor[1];
                String email = instructor[2];
                String bio = instructor[instructor.length-1];
                for(int index = 3; index < 7; index++)
                {
                    if(instructor[index].equals("1"))
                    {
                        strokes[index-3] = true;
                    }
                    else
                    {
                        strokes[index-3] = false;
                    }
                }
                for(int index = 7; index < 19; index++)
                {
                    if(instructor[index].equals("1"))
                    {
                        availability[index-7] = true;
                    }
                    else
                    {
                        availability[index-7] = false;
                    }
                }

                Instructor temp = new Instructor(name,email,bio,strokes,availability );
                instructorList.add(temp);

                listBuffer.append(readLine);
                readLine = input.readLine();
            }
            input.close();
            fileAsString = listBuffer.toString();
        }
        catch (IOException fileError)
        {
            fileAsString = "failed to read";
        }
        //parse to list

    }

    private void convertBiosToList(String biosFile)
    {
        Scanner biosReader = new Scanner(biosFile);
        while(biosReader.hasNext())
        {
            String [] instructor = biosReader.nextLine().split(",");
            boolean [] availability = new boolean[12];
            boolean [] strokes = new boolean[4];
            String name = instructor[0] + " "+instructor[1];
            String email = instructor[2];
            String bio = instructor[instructor.length-1];
            for(int index = 3; index < 7; index++)
            {
                if(instructor[index].equals("1"))
                {
                    strokes[index-3] = true;
                }
                else
                {
                    strokes[index-3] = false;
                }
            }
            for(int index = 7; index < 19; index++)
            {
                if(instructor[index].equals("1"))
                {
                    availability[index-7] = true;
                }
                else
                {
                    availability[index-7] = false;
                }
            }

            Instructor temp = new Instructor(name,email,bio,strokes,availability );
            instructorList.add(temp);
        }
    }

    private void loadListToView(ArrayList<Instructor> currentList)
    {
        biosAdapter = new InstructorAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, currentList);
        instructorListView.setAdapter(biosAdapter);
        biosAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        instructorList = new ArrayList<Instructor>();
        clicked = false;
        View returnedView = inflater.inflate(R.layout.fragment_bios, container, false);
        instructorListView = (ListView) returnedView.findViewById(R.id.instructorListView);
        timeGroup = (RadioGroup) returnedView.findViewById(R.id.time_menu);
        clearSelectionButton = (Button) returnedView.findViewById(R.id.clearSelectionButton);
        dayGroup = (RadioGroup) returnedView.findViewById(R.id.day_menu);
        strokeGroup = (RadioGroup) returnedView.findViewById(R.id.stroke_menu);
        //check if external data exists
        if(false)
        {
            //TODO: look for updated bios file on website. Save to external storage
        }
        else
        {
            openBiosFile();
            loadListToView(instructorList);
        }

        setupListeners();

        // Inflate the layout for this fragment
        return returnedView;
    }

    private void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void setupListeners()
    {
        instructorListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                String [] address = {instructorList.get(position).getEmail()};
                String subject = "I would like to arrange a private lesson ...";
                composeEmail(address, subject);

                return false;
            }
        });

        instructorListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView bioView = (TextView) view.findViewById(R.id.instructor_bio_text);
                if(clicked)
                {
                    bioView.setMaxLines(1);
                }
                else
                {
                    bioView.setMaxLines(10);
                }
                clicked = !clicked;

            }
        });

        timeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                    filteredList = new ArrayList<Instructor>();


                if(checkedId == R.id.am_box)
                {
                    for(Instructor current : instructorList)
                    {
                        if(current.getAvailability()[0])
                        {
                            filteredList.add(current);
                        }
                    }

                    loadListToView(filteredList);

                }
                else if(checkedId == R.id.pm_box)
                {
                    for(Instructor current : instructorList)
                    {
                        if(current.getAvailability()[1])
                        {
                            filteredList.add(current);
                        }
                    }
                }
                else
                {
                    loadListToView(instructorList);
                }

            }
        });

        dayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                    filteredList = new ArrayList<Instructor>();

                if(checkedId == R.id.weekday_radio)
                {
                    for(Instructor current : instructorList)
                    {
                        for (int dayIndex = 0; dayIndex < 10; dayIndex++)
                        {
                            if (current.getAvailability()[dayIndex])
                            {
                                filteredList.add(current);
                                break;
                            }
                        }
                    }
                }
                else if(checkedId == R.id.saturday_radio)
                {
                    for(Instructor current : instructorList)
                    {
                        for (int dayIndex = 10; dayIndex < 12; dayIndex++)
                        {
                            if (current.getAvailability()[dayIndex])
                            {
                                filteredList.add(current);
                                break;
                            }
                        }
                    }

                }
            }
        });

        strokeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                    filteredList = new ArrayList<Instructor>();


                if(checkedId == R.id.flyBox)
                {
                    for(Instructor current : instructorList)
                    {
                        if(current.getStrokes()[0])
                        {
                            filteredList.add(current);
                        }
                    }
                }
                else if(checkedId == R.id.backBox)
                {
                    for(Instructor current : instructorList)
                    {
                        if(current.getStrokes()[1])
                        {
                            filteredList.add(current);
                        }
                    }
                }
                else if(checkedId == R.id.breastBox)
                {
                    for(Instructor current : instructorList)
                    {
                        if(current.getStrokes()[2])
                        {
                            filteredList.add(current);
                        }
                    }
                }
                else if(checkedId == R.id.freeBox)
                {
                    for(Instructor current : instructorList)
                    {
                        if(current.getStrokes()[3])
                        {
                            filteredList.add(current);
                        }
                    }
                }
                else
                {
                    filteredList = (ArrayList<Instructor>) instructorList.clone();
                }

                loadListToView(filteredList);
            }
        });

        clearSelectionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dayGroup.clearCheck();
                timeGroup.clearCheck();
                strokeGroup.clearCheck();
                loadListToView(instructorList);
                filteredList = null;
            }
        });



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }


    private void applyFilter()
    {


    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
