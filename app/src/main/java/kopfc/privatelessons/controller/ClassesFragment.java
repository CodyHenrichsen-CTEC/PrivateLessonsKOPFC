package kopfc.privatelessons.controller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.ParcelFileDescriptor;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassesFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private WebView classesView;

    public ClassesFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassesFragment newInstance(String param1, String param2)
    {
        ClassesFragment fragment = new ClassesFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.fragment_classes, null);
        classesView = (WebView) baseView.findViewById(R.id.classesView);
        String derp  = "file:///android_asset/classes.html";
        classesView.getSettings().setLoadWithOverviewMode(true);
        classesView.getSettings().setUseWideViewPort(true);
        classesView.getSettings().setJavaScriptEnabled(true);


        classesView.setWebViewClient(new ClassesViewClient());

        classesView.loadUrl(derp);

        Resources systemResources = getResources();
        float fontSize = systemResources.getDimension(R.dimen.textSize);

        classesView.getSettings().setDefaultFontSize((int) fontSize);
        classesView.getSettings().setTextZoom(200);
        classesView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) view;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;}
                            break;}}
                return false;
            }
        });

        return baseView;
    }

    private class ClassesViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            Toast.makeText(view.getContext(), "OVERRIDE", Toast.LENGTH_LONG).show();

            return false;
        }

        @Override
        public void onPageFinished(WebView view, final String url) {

            Toast.makeText(view.getContext(), "PAGE FINISHED", Toast.LENGTH_LONG).show();
        }
    }

    public void onViewCreated(View view, Bundle savedInstance)
    {
        super.onViewCreated(view, savedInstance);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
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
