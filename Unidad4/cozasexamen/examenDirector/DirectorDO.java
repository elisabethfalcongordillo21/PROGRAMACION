
/**
 * Data Object que representa un director de cine en la BD
 */
public class DirectorDO {

    private int id;
    private String nombre;
    private String nacionalidad;
    private int anyo_nacimiento;

    public DirectorDO() {}

    public DirectorDO(int id, String nombre, String nacionalidad, int anyo_nacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.anyo_nacimiento = anyo_nacimiento;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getNacionalidad() { return nacionalidad; }
    public int getAnyo_nacimiento() { return anyo_nacimiento; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public void setAnyo_nacimiento(int anyo_nacimiento) { this.anyo_nacimiento = anyo_nacimiento; }

    @Override
    public String toString() {
        return "[ID: " + id + "] " + nombre + " | Nacionalidad: " + nacionalidad + " | Año nacimiento: " + anyo_nacimiento;
    }
}