package org.openbot.main;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.ficat.easyble.BleDevice;
import java.util.List;
import org.openbot.R;

public class ScanDeviceAdapter extends CommonRecyclerViewAdapter<BleDevice> {
  public ScanDeviceAdapter(
      @NonNull Context context,
      @NonNull List<BleDevice> dataList,
      @NonNull SparseArray<int[]> resLayoutAndViewIds) {
    super(context, dataList, resLayoutAndViewIds);
  }

  @Override
  public int getItemResLayoutType(int position) {
    return R.layout.ble_listview_tv;
  }

  @Override
  public void bindDataToItem(MyViewHolder holder, BleDevice data, int position) {
    TextView tvName = (TextView) holder.mViews.get(R.id.ble_name);
    TextView tvAddress = (TextView) holder.mViews.get(R.id.ble_address);
    TextView tvConnectionState = (TextView) holder.mViews.get(R.id.ble_connection_state);
    tvName.setText(data.name);
    tvAddress.setText(data.address);
    if (data.connecting) {
      tvConnectionState.setText("Connecting");
      tvConnectionState.setTextColor(Color.BLUE);
    } else if (data.connected) {
      tvConnectionState.setText("Disconnect");
      tvConnectionState.setTextColor(Color.RED);
    } else if (!data.connected) {
      tvConnectionState.setText("Connect");
      tvConnectionState.setTextColor(Color.BLACK);
    }
  }
}
