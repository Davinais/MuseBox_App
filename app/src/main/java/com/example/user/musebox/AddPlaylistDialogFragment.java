package com.example.user.musebox;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

public class AddPlaylistDialogFragment extends DialogFragment {

    public interface AddPlaylistDialogListener {
        void onCreateButtonClick(DialogFragment dialog);
    }

    AddPlaylistDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle(R.string.create_playlist)
               .setIcon(R.mipmap.ic_launcher)
               .setView(inflater.inflate(R.layout.dialog_addplaylist, null))
               .setPositiveButton(R.string.create_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onCreateButtonClick(AddPlaylistDialogFragment.this);
                    }
               })
               .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddPlaylistDialogFragment.this.getDialog().cancel();
                    }
               });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (AddPlaylistDialogListener) context;
    }
}
