package unis.edu.crudalunos.enums;

import java.util.HashMap;
import java.util.Map;

public enum UsuarioTipo {
    ALUNO(1),
    PROFESSOR(2);

    private int value;
    private static Map map = new HashMap<>();

    private UsuarioTipo(int value) {
        this.value = value;
    }

    static {
        for (UsuarioTipo pageType : UsuarioTipo.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static UsuarioTipo valueOf(int pageType) {
        return (UsuarioTipo) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}
