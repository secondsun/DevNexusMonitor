package org.devnexus.devnexusmonitor.display;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.devnexus.devnexusmonitor.R;
import org.devnexus.devnexusmonitor.service.MediatorService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by summers on 1/1/17.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class SetUpFragment extends Fragment {

    private final BroadcastReceiver fcmTokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            displayTokenQRCode(intent.getStringExtra(MediatorService.EXTRA_FCM_TOKEN));
        }
    };
    private View view;

    private void displayTokenQRCode(String fcmToken) {
        if (fcmToken == null || fcmToken.isEmpty()) {
            return;
        }
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(fcmToken, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) view.findViewById(R.id.img_result_qr)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return (this.view = inflater.inflate(R.layout.setup, null));
    }

    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(fcmTokenReceiver, new IntentFilter(MediatorService.EVENT_FCM_TOKEN));

        getActivity().startService(new Intent(getActivity(), MediatorService.class)
                        .setAction(MediatorService.ACTION_EMIT_FCM_TOKEN)
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(fcmTokenReceiver);
    }

    public static SetUpFragment newInstance() {
        return new SetUpFragment();
    }
}
