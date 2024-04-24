package org.openbot.main;

import android.os.Bundle;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import org.openbot.R;
import org.openbot.vehicle.Vehicle;
import timber.log.Timber;

public class UsbFragment extends PreferenceFragmentCompat {

  private MainViewModel mViewModel;
  private SwitchPreferenceCompat connection;
  private Vehicle vehicle;

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.usb_connections, rootKey);

    mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    vehicle = mViewModel.getVehicle().getValue();

    connection = findPreference("connection");
    if (connection != null) {
      connection.setTitle("No Device");
      if (vehicle != null && vehicle.isUsbConnected()) {
        connection.setChecked(true);
        connection.setTitle(vehicle.getUsbConnection().getProductName());
      } else {
        connection.setTitle("No Device");
        connection.setChecked(false);
      }
      connection.setOnPreferenceClickListener(
          preference -> {
            Timber.d(String.valueOf(connection.isChecked()));
            if (vehicle != null) {
              if (connection.isChecked()) {
                vehicle.connectUsb();
                if (vehicle.isUsbConnected())
                  connection.setTitle(vehicle.getUsbConnection().getProductName());
                else {
                  connection.setTitle("No Device");
                  connection.setChecked(false);
                  Toast.makeText(
                          requireContext().getApplicationContext(),
                          "Please check the USB connection.",
                          Toast.LENGTH_SHORT)
                      .show();
                }
              } else {
                vehicle.disconnectUsb();
                connection.setTitle("No Device");
              }
              mViewModel.setUsbStatus(vehicle.isUsbConnected());
            }
            return true;
          });
    }
  }
}
