package com.enihsyou.shane.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 111;
    private static final int REQUEST_CONTACT = 695;
    private static final int REQUEST_PHOTO = 290;
    private Crime mCrime;
    private File mPhotoFile;
    /*View widgets*/
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mReportButton;
    private Button mSuspectButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    public static Fragment newInstance(UUID crimeID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeID);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String getCrimeReport() {
        String solvedString;
        if (mCrime.isSolved()) solvedString = getString(R.string.crime_report_solved);
        else solvedString = getString(R.string.crime_report_unsolved);

        String dateFormat = "EEE, MMM dd";
        String dataString = DateFormat.format(dateFormat, mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null) suspect = getString(R.string.crime_report_no_suspect);
        else suspect = getString(R.string.crime_report_suspect);

        String report = getString(R.string.crime_report, mCrime.getTitle(), dateFormat, solvedString, suspect);
        return report;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = CrimeLab.get(getActivity()).getCrime((UUID) getArguments().getSerializable(ARG_CRIME_ID));
        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime);
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mDateButton = (Button) view.findViewById(R.id.crime_date);
        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSuspectButton = (Button) view.findViewById(R.id.crime_suspect);
        mReportButton = (Button) view.findViewById(R.id.crime_report);
        mPhotoButton = (ImageButton) view.findViewById(R.id.crime_camera);
        mPhotoView = (ImageView) view.findViewById(R.id.crime_photo);

        PackageManager packageManager = getActivity().getPackageManager();
        /*mTitleField*/
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /*mDateButton*/
        updateDate(mCrime.getDate());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        /*mSolvedCheckBox*/
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {mCrime.setSolved(isChecked);}
        });
        /*mSuspectButton*/
        if (mCrime.getSuspect() != null) mSuspectButton.setText(mCrime.getSuspect());
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null)
            mSuspectButton.setEnabled(false);
        /*mReportButton*/
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));

                intent = Intent.createChooser(intent, getString(R.string.send_report));

                startActivity(intent);
            }
        });
        /*mPhotoButton*/
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto) {
            Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
        /*mPhotoView*/
        updatePhotoView();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_DATE:
                Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mCrime.setDate(date);
                updateDate(mCrime.getDate());
                break;
            case REQUEST_CONTACT:
                Uri contactUri = data.getData();
                String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};

                Cursor cursor = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);
                try {
                    if (cursor.getCount() == 0) return;
                    cursor.moveToFirst();
                    String suspect = cursor.getString(0);
                    mCrime.setSuspect(suspect);
                    mSuspectButton.setText(suspect);
                } finally {
                    cursor.close();
                }
                break;
            case REQUEST_PHOTO:
                updatePhotoView();
        }
    }

    private void updateDate(Date date) {
        mDateButton.setText(date.toString());
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) mPhotoView.setImageDrawable(null);
        else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }

    }
}
