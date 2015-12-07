package pl.checkersmobile.utils;

import android.support.v4.app.FragmentManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by paulc_000 on 2015-11-24.
 */
public class Utils {

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static void showProgressDialog(FragmentManager suportFragmentMenager, String title) {
        ProgressDialogFragment progressDialogFragment =
                ProgressDialogFragment.newInstance(title);
        progressDialogFragment.setCancelable(true);
        progressDialogFragment.show(suportFragmentMenager, ProgressDialogFragment.Dialog);
    }

    public static void hideProgressDialog(FragmentManager suportFragmentMenager) {
        ProgressDialogFragment progressDialogFragment =
                (ProgressDialogFragment) suportFragmentMenager.findFragmentByTag(ProgressDialogFragment.Dialog);
        if (progressDialogFragment != null && progressDialogFragment.isAdded()) {
            progressDialogFragment.dismiss();
        }
    }
}
