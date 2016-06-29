package kopfc.privatelessons.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kopfc.privatelessons.model.Instructor;

/**
 * @author Cody Henrichsen
 * @version 6/24/16
 */
public class InstructorAdapter extends ArrayAdapter<Instructor>
{
    private int resourceId;
    public InstructorAdapter(Context context, int textViewResourceId, List<Instructor> objects)
    {
        super(context,  textViewResourceId, objects);
        resourceId = textViewResourceId;
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
        try
        {
            View listItemView = convertView;
            final InstructorHolder currentHolder;
            Instructor current = getItem(position);
            String name = current.getName().replaceAll("\\s","");
            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                listItemView = inflater.inflate(R.layout.sample_instructor_view, null);

                currentHolder = new InstructorHolder();
                currentHolder.nameView = (TextView) listItemView.findViewById(R.id.display_name);
                currentHolder.bioView = (TextView) listItemView.findViewById(R.id.display_bio);
                currentHolder.instructorImage = (ImageView) listItemView.findViewById(R.id.display_image);

                int instructorImageResource = getContext().getResources().getIdentifier(name, "drawable", getContext().getPackageName());
                if(instructorImageResource != 0)
                {
                    currentHolder.instructorImage.setImageResource(instructorImageResource);
                }
                else
                {
                    currentHolder.instructorImage.setImageResource(R.drawable.noname);
                }

                listItemView.setTag(currentHolder);
            }
            else
            {
                currentHolder = (InstructorHolder) listItemView.getTag();
                listItemView = convertView;
            }

            return listItemView;


        }
        catch (Exception error)
        {
            return null;
        }
    }

}
