package unis.edu.crudalunos.helpers;

import unis.edu.crudalunos.model.Usuario;

public class app_parameters {
    private static volatile Usuario usuario;

    private app_parameters() {
        if (usuario != null) {
            throw new RuntimeException("Use getLoggedUser() method to get the single instance");
        }
    }

    public static Usuario getLoggedUser() {
        if(usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public static void setLoggedUser(Usuario _usuario) {
        usuario = _usuario;
    }
}
