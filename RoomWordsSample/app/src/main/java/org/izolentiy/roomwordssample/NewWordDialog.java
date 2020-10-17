package org.izolentiy.roomwordssample;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class NewWordDialog extends DialogFragment {
    public interface DialogListener {
        void onDialogPositiveClick(NewWordDialog dialog);
    }
    DialogListener mListener;
    String mWord;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the callback interface.
            throw new ClassCastException(context.toString()
                    + " must implement callback interface.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater.
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Title
        builder.setTitle(R.string.dialog_title);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because it's going in the dialog layout.
        builder.setView(inflater.inflate(R.layout.new_word_dialog, null));
        // Save button
        builder.setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editTextWord = getDialog().findViewById(R.id.word_edittext);
                mWord = editTextWord.getText().toString();
                // Send the positive button event back to the host activity/fragment.
                mListener.onDialogPositiveClick(NewWordDialog.this);
            }
        });
        return builder.create();
    }

    public String getWord() {
        return this.mWord;
    }
}
