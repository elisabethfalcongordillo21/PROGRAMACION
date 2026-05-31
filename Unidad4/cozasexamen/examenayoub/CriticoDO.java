public class CriticoDO {
 
    private int id;
    private String nombre;
    private String medio;       // periódico/revista/web donde escribe
    private int anyo_inicio;    // año en que empezó a ejercer la crítica
 
    public CriticoDO() {}
 
    public CriticoDO(int id, String nombre, String medio, int anyo_inicio) {
        this.id = id;
        this.nombre = nombre;
        this.medio = medio;
        this.anyo_inicio = anyo_inicio;
    }
 
    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMedio() { return medio; }
    public int getAnyo_inicio() { return anyo_inicio; }
 
    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMedio(String medio) { this.medio = medio; }
    public void setAnyo_inicio(int anyo_inicio) { this.anyo_inicio = anyo_inicio; }
 
    @Override
    public String toString() {
        return "[ID: " + id + "] " + nombre + " | Medio: " + medio + " | Año inicio: " + anyo_inicio;
    }
}
