package com.example.maripes.listdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;


/**
 * An activity representing a list of Drivers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DriverDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link DriverListFragment} and the item details
 * (if present) is a {@link DriverDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link DriverListFragment.Callbacks} interface
 * to listen for item selections.
 */
//10. implementamos una interfaqz definida en el fragmento para saber que hacer cuando el fragmento nos diga de cerrar y creamos el metodo cerrar que esta abajo
public class DriverListActivity extends AppCompatActivity
        implements DriverListFragment.Callbacks, DriverDetailFragment.MyFragmentListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    // 1 Esta variable controla si esta mostrando un panel o dos
    private boolean mTwoPane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_app_bar);
        //1 Definimos el layout a cargar por parte de la actividad


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //3. Si hay panel de la derecha (landscape), pongo boolean a true y cargo el fragment de la lista (izq) o master
        if (findViewById(R.id.driver_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.

            ((DriverListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.driver_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link DriverListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {

        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction

            Bundle arguments = new Bundle();
            arguments.putString(DriverDetailFragment.ARG_ITEM_ID, id);
            DriverDetailFragment fragment = new DriverDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.driver_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.

            //Si no hay fragment de la derecha, al hacer click, llamo a otra activity
            Intent detailIntent = new Intent(this, DriverDetailActivity.class);
            detailIntent.putExtra(DriverDetailFragment.ARG_ITEM_ID, id);
            startActivityForResult(detailIntent, 100);
        }
    }

    //Recojo el fragmento del detail y lo borro
    @Override
    public void cerrar() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.driver_detail_container);
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:

                break;
        }
    }
}