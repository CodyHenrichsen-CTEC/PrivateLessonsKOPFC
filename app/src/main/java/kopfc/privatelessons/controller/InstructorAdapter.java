package kopfc.privatelessons.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kopfc.privatelessons.model.Instructor;

/**
 * @author Cody Henrichsen
 * @version 6/24/16
 */
public class InstructorAdapter extends ArrayAdapter<Instructor>
{
    private int resourceId;
    private LayoutInflater inflater;
    private List<Instructor> allInstructors;
    private List<Instructor> filteredInstructors;

    public InstructorAdapter(Context context, int textViewResourceId, List<Instructor> objects)
    {
        super(context,  textViewResourceId, objects);
        allInstructors = objects;
        filteredInstructors = objects;

        resourceId = textViewResourceId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class InstructorHolder
    {
        public TextView nameView;
        public TextView bioView;
        public ImageView instructorImage;
    }




    private void loadImage()
    {
        InstructorHolder temp = new InstructorHolder();
        String name = "";

        int instructorImageResource = getContext().getResources().getIdentifier(name, "drawable", getContext().getPackageName());
        if(instructorImageResource != 0)
        {
            temp.instructorImage.setImageResource(instructorImageResource);
        }
        else
        {
            temp.instructorImage.setImageResource(R.drawable.noname);
        }

    }


    public View getView(int position, View convertView, ViewGroup parent)
    {
        InstructorHolder currentHolder=null;
        View instructorRowItem = convertView;
        if(instructorRowItem == null)
        {
            try
            {
                instructorRowItem = inflater.inflate(R.layout.instructor_row_view, null);

                currentHolder = new InstructorHolder();

                currentHolder.nameView = (TextView) instructorRowItem.findViewById(R.id.instructor_name_text);
                currentHolder.bioView = (TextView) instructorRowItem.findViewById(R.id.instructor_bio_text);
                currentHolder.instructorImage = (ImageView) instructorRowItem.findViewById(R.id.instructor_image);

                instructorRowItem.setTag(currentHolder);
            }
            catch (Exception error)
            {

            }
        }
        else
        {
            currentHolder = (InstructorHolder) instructorRowItem.getTag();
        }

        Instructor current = getItem(position);
        String name = current.getName().replaceAll("\\s", "").toLowerCase();

        currentHolder.nameView.setText(current.getName());
        currentHolder.bioView.setText(current.getBiography());
        int instructorImageResource = getContext().getResources().getIdentifier(name, "drawable", getContext().getPackageName());

        if (instructorImageResource != 0)
        {
            currentHolder.instructorImage.setImageResource(instructorImageResource);
        }
        else
        {
            currentHolder.instructorImage.setImageResource(R.drawable.noname);
        }


        return instructorRowItem;
    }

}

