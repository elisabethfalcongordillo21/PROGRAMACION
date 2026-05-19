package examen.model;

public class ClienteDO {
    private int    id_cliente;
    private String dni;
    private String nombre;
    private String telefono;
    private String tipo;    // Basico | Premium | Partner | VIP
    private String estatus; // Activo | Inactivo


    public ClienteDO(int id, String dni, String nombre,String telefono, String tipo, String estatus) {
        this.id_cliente = id;
        this.dni        = dni;
        this.nombre     = nombre;
        this.telefono   = telefono;
        this.tipo       = tipo;
        this.estatus    = estatus;
    }


    public ClienteDO(String dni, String nombre, String telefono, String tipo, String estatus) {
      
        this(0, dni, nombre, telefono, tipo, estatus);
    }


    public int getId_cliente() {
        return this.id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstatus() {
        return this.estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
 

    @Override
    public String toString() {
        return id_cliente + " | " + nombre + " | " + tipo + " | " + estatus;
    }
}