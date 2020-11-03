package unis.edu.crudalunos.enums;

import android.widget.SearchView;

import java.util.HashMap;
import java.util.Map;

public enum  Semana {
    DOMINGO (1),
    SEGUNDA (2),
    TERCA (3),
    QUARTA (4),
    QUINTA (5),
    SEXTA (6),
    SABADO (7);

    private int value;
    private static Map map = new HashMap<>();

    Semana(int value){
        this.value = value;
    }

    static {
        for (Semana pageType : Semana.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public int getValue() {
        return value;
    }
}
