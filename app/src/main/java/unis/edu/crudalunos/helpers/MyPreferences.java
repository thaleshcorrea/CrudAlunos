package unis.edu.crudalunos.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class MyPreferences {
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;

    public MyPreferences(Context context, String arquivo) {
        preferences = context.getSharedPreferences(arquivo, 0);
        editor = preferences.edit();
    }

    abstract public void set(int dados);

    abstract public int get();

    abstract public boolean exist();

    abstract public void remove();
}
