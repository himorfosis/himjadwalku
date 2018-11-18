package com.himorfosis.jadwalku;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TabKalender extends Fragment {

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    ViewPager viewPager;
    Fragment fragment;
    String tanggalawal;
    String tanggalakhir;
    Database db;


    //Overriden method onCreateView

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
            //     caldroidFragment.setBackgroundDrawableForDate(R.color.colorPrimary);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.tabkalender, container, false);
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Kalender");

        Calendar c = Calendar.getInstance();

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate1 = formatter.format(c.getTime());


        caldroidFragment = new CaldroidFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh

        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();


        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                Toast.makeText(getContext(), formatter.format(date), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChangeMonth(int month, int year) {
                Calendar cal = Calendar.getInstance();
                String text = "month: " + month + " year: " + year;
//                Toast.makeText(getContext(), text,
//                        Toast.LENGTH_SHORT).show();
                cal.set(year, month-1, 1);
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                tanggalawal = df2.format(cal.getTime());
                int maxtgl = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                cal.set(year, month-1, maxtgl);
                tanggalakhir = df2.format(cal.getTime());
                Log.d("GETKALENDAR", " = "+tanggalawal + "=="+tanggalakhir);
                //  tabLayout.getTabAt(0).select();



            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
//                    Toast.makeText(getContext(),
//                            "Caldroid view is created", Toast.LENGTH_SHORT)
//                            .show();
                }
            }

        };

        caldroidFragment.setCaldroidListener(listener);


    }
}
