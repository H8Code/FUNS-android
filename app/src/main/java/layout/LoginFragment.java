package layout;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.h8code.funs.funs.R;

/**
 * Created by belial on 21.03.16.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LOGIN_FRAGMENT";
    private onLoginButtonPressedListener login_listener;
    private EditText login_text;
    private EditText password_text;
    private Button login_button;

    private static final String DEFAULT_LOGIN = "default_name";

    public interface onLoginButtonPressedListener {
        void onLoginButtonPressed(String login, String password);
    }

    public LoginFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, null);
        Log.d(TAG, "onCreateView");

        try {
            login_listener = (onLoginButtonPressedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must be implemented!");
        }

        login_text = (EditText) v.findViewById(R.id.login_text_edit);
        password_text = (EditText) v.findViewById(R.id.password_text_edit);
        login_button = (Button) v.findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_listener.onLoginButtonPressed(
                        login_text.getText().toString(),
                        password_text.getText().toString());
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            login_listener = (onLoginButtonPressedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must be implemented!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        login_listener = null;
    }
}
