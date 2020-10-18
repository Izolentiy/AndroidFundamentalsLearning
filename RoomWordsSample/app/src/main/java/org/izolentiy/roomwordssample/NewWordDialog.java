package org.izolentiy.roomwordssample;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class NewWordDialog extends DialogFragment {
    public interface DialogListener {
        void onDialogPositiveClick(NewWordDialog dialog);
    }
    private DialogListener mListener;
    private EditText mEditText;

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
        // Get the layout inflater.
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because it's going in the dialog layout.
        View view = inflater.inflate(R.layout.new_word_dialog, null);
        mEditText = view.findViewById(R.id.word_edittext);

        final Bundle args = getArguments();
        if (args != null) {
            String word = args.getString("WORD_KEY", "lol");
            int id = args.getInt("ID_KEY", -1);
            mEditText.setText(word);
            mEditText.setSelection(word.length());
            mEditText.requestFocus();
        }

        Log.d("DEBUG_TAG", "dialog building start");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (args != null)
            builder.setTitle(R.string.update_word);
        else
            builder.setTitle(R.string.new_word);
        builder.setView(view);
        // Save button
        builder.setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String word = NewWordDialog.this.mEditText.getText().toString();

                if (args != null) {
                    args.putString("WORD_KEY", word);
                    NewWordDialog.this.setArguments(args);
                } else {
                    Bundle args = new Bundle();
                    args.putString("WORD_KEY", word);
                    NewWordDialog.this.setArguments(args);
                }
                // Send the positive button event back to the host activity/fragment.
                mListener.onDialogPositiveClick(NewWordDialog.this);
            }
        });
        return builder.create();
    }
}
