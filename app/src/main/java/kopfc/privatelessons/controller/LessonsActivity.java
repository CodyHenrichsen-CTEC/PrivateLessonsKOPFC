package kopfc.privatelessons.controller;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class LessonsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                        BiosFragment.OnFragmentInteractionListener,
                        ClassesFragment.OnFragmentInteractionListener,
                        PaymentFragment.OnFragmentInteractionListener,
                        DrillsFragment.OnFragmentInteractionListener,
                        ScheduleFragment.OnFragmentInteractionListener
{


    private String currentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentTitle = getString(R.string.app_name);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    /**
     * Menu options disabled for this app as they are not needed.
     * @param menu
     * @return Disabled access.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        // Handle navigation view item clicks here.
        displayFragmentInWindow(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void displayFragmentInWindow(int fragmentViewId)
    {
        Fragment updatedFragment = null;
        String title = getString(R.string.app_name);

        switch (fragmentViewId)
        {
            case R.id.nav_schedule:
                updatedFragment = new ScheduleFragment();
                title = "Schedule Lessons";
                break;
            case R.id.nav_bios:
                updatedFragment = new BiosFragment();
                title = "Instructor Bios";
                break;
            case R.id.nav_classes:
                updatedFragment = new ClassesFragment();
                title = "Classes";
                break;
            case R.id.nav_drills:
                updatedFragment = new DrillsFragment();
                title = "Stroke Drills";
                break;
            case R.id.nav_pay:
                updatedFragment = new PaymentFragment();
                Toast.makeText(this.getBaseContext(), getText(R.string.reminder), Toast.LENGTH_LONG).show();
                title = "Payment";
                break;
        }

        if (updatedFragment != null)
        {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.lesson_content, updatedFragment);
            ft.addToBackStack(title);
            this.setTitle(title);
            currentTitle = title;
            ft.commit();
        }

    }

    /**
     * Required method for Fragment interaction for all subcomponents.
     * @param uri
     */
    public void onFragmentInteraction(Uri uri)
    {

    }

}
