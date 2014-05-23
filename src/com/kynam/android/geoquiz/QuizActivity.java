package com.kynam.android.geoquiz;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class QuizActivity extends ActionBarActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    
    
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
    	new TrueFalse(R.string.question_ocean, true),
    	new TrueFalse(R.string.question_mideast, false),
    	new TrueFalse(R.string.question_africa, false),
    	new TrueFalse(R.string.question_americas, true),
    	new TrueFalse(R.string.question_asia, true),
    };
    
    private int mCurrentIndex = 0;
    
    private boolean mIsCheater;
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (data == null) {
    		return;
    	}
    	mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }
    
    private void updateQuestion(){
    	int question = mQuestionBank[mCurrentIndex].getQuestion();
    	mQuestionTextView.setText(question);
    }
    
    private void checkAnswer(boolean userPressTrue){
    	boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
    	
    	int messageResID = 0;
    	
    	if (mIsCheater) {
    		messageResID = R.string.judgment_toast;
    	} else {
    	
	    	if (userPressTrue == answerIsTrue) {
	    		messageResID = R.string.correct_toast;
	    	} else {
	    		messageResID = R.string.incorrect_toast;
	    	}
    	}
    	Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();
    }
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
			}
		});
        
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i, 0);
			}
		});
                
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnswer(true);
				
			}
		});
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnswer(false);
				
			}
		});
        
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
			}
		});
        
        mPreviousButton = (Button)findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mCurrentIndex == 0) {
					mCurrentIndex = mQuestionBank.length - 1; 
				} else
				mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
				updateQuestion();
			}
		});
        
        if (savedInstanceState != null) {
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        
        updateQuestion();

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        
        
    }
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() called");
	}
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
            return rootView;
        }
    }

}
