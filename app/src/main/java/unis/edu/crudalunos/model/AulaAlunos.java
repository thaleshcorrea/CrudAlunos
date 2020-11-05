package unis.edu.crudalunos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"aulaId", "usuarioId"})
public class AulaAlunos {
    @ForeignKey(entity = Aula.class, parentColumns = "aulaId", childColumns = "aulaId")
    long aulaId;

    @ForeignKey(entity = Usuario.class, parentColumns = "usuarioId", childColumns = "usuarioId")
    long usuarioId;

    @NonNull
    boolean presente;
}
