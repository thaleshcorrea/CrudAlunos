package unis.edu.crudalunos.helpers;

import android.content.Context;

public class UserPreferences extends MyPreferences {


    public UserPreferences(Context context, String arquivo) {
        super(context, arquivo);
    }

    @Override
    public void set(int dados) {
        editor.putInt("id", dados);
        editor.commit();
    }

    @Override
    public int get() {
        if(preferences.contains("id")) {
            return preferences.getInt("id", 0);
        } else {
            return 0;
        }
    }

    @Override
    public boolean exist() {
        return (preferences.contains("id"));
    }

    @Override
    public void remove() {
        editor.remove("id");
        editor.commit();
    }
}
